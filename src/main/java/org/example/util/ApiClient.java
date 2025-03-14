package org.example.util;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final String POLYGON_API_KEY = "kApY0sl9MBHHa_E1AVxZdgrWW3AWlPEd";
    private static final String ALPHA_VINTAGE_API_KEY = "AUJP70BDGGUOLTS5";

    public static String fetchStockData(String apiCall) throws IOException {
        URL apiCallUrl = new URL(apiCall);
        HttpURLConnection apiConnection = (HttpURLConnection) apiCallUrl.openConnection();
        int httpResponse = apiConnection.getResponseCode();

        if(httpResponse == 200){
            BufferedReader in = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
        else{
            throw new IOException("Http request has failed with the response code: " + httpResponse);
        }
    }

    public static @NotNull String getGeneralStockData(String tickerSymbol) throws IOException {
        return fetchStockData(String.format("https://api.polygon.io/v3/reference/tickers/%s?apiKey=%s",
                tickerSymbol, POLYGON_API_KEY
        ));
    }

}
