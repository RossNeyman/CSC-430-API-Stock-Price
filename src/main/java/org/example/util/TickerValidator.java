package org.example.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TickerValidator {
    public static void main(String[] args) {

            List<String> validSymbols = new ArrayList<>();
            String resourcePath = "tickers/top100tickers";
            int tickersValidated = 0;

            try (InputStream inputStream = TickerValidator.class.getClassLoader().getResourceAsStream(resourcePath)) {
                assert inputStream != null;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        if((Objects.requireNonNull(SerDe.deserializeJsonStockResponse(ApiClient.getGeneralStockData(line.trim())))).getListDate() != null)
                            validSymbols.add(line.trim());
                        System.out.println("Checked symbol. Waiting for api..." + tickersValidated++);
                        TimeUnit.SECONDS.sleep(2);
                    }
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("tickers/validTickers"))) {
                for (String symbol : validSymbols) {
                    writer.write(symbol);
                    writer.newLine();
                }
                System.out.println("Valid tickers written to tickers/validTickers");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }


        System.out.println(validSymbols.size());

    }
}
