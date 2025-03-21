package org.example.util;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


//This class is a utility for validating that the tickers in the resource file return a list date from the api.
//It is to be used whenever the file with stock tickers is changed, but is not run from Main.
public class TickerValidator {
    public static void main(String[] args) {
        List<String> validSymbols = new ArrayList<>();
        String resourcePath = "tickers/top100tickers";
        int tickersValidated = 0;
        int tickersFound = 0;

        try (InputStream inputStream = TickerValidator.class.getClassLoader().getResourceAsStream(resourcePath)) {
            //ensures file is not empty
            if (inputStream == null) {
                System.err.println("Resource not found: " + resourcePath);
                return;
            }

            //reads through file, adds
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        String ticker = line.trim();
                        System.out.println("Checking symbol: " + ticker + " (" + (++tickersValidated) + ")");

                        var response = ApiClient.getGeneralStockData(ticker);
                        var stockData = SerDe.deserializeJsonStockResponse(response);
                        if (stockData == null) {
                            System.out.println("Failed to deserialize data for: " + ticker);
                            continue;
                        }

                        if (stockData.getListDate() != null && isValidDateFormat(stockData.getListDate())) {
                            validSymbols.add(ticker);
                            System.out.println("Valid ticker found: " + ticker + " (" + (++tickersFound) + ")");
                        } else {
                            System.out.println("Ticker has null listDate: " + ticker);
                        }

                        //timeout the api calls as to not go over the 5 call per minute per key limit
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        System.err.println("Error processing ticker: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading resource: " + resourcePath);
            e.printStackTrace();
        }

        System.out.println("Total valid symbols found: " + validSymbols.size());

        // Write to file if we have valid symbols
        if (!validSymbols.isEmpty()) {
            try {
                File outputFile = new File("tickers/validTickers");

                // Ensure the parent directory exists
                File parentDir = outputFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                    for (String symbol : validSymbols) {
                        writer.write(symbol);
                        writer.newLine();
                    }
                    System.out.println("Valid tickers written to: " + outputFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("No valid tickers found to write to file.");
        }
    }

    public static boolean isValidDateFormat(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}