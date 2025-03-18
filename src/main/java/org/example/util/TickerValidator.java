package org.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.io.File;

public class TickerValidator {
    public static void main(String[] args) {

            List<String> validSymbols = new ArrayList<>();
            String resourcePath = "tickers/top100tickers";

            try (InputStream inputStream = TickerValidator.class.getClassLoader().getResourceAsStream(resourcePath)) {
                assert inputStream != null;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        if((Objects.requireNonNull(SerDe.deserializeJsonStockResponse(ApiClient.getGeneralStockData(line.trim())))).getListDate() != null)
                            validSymbols.add(line.trim());
                        System.out.println("Checked symbol. Waiting for api...");
                        TimeUnit.SECONDS.sleep(15);
                    }
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }


            System.out.println(validSymbols.size());

    }
}
