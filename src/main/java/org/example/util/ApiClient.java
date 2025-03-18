package org.example.util;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String POLYGON_API_KEY = dotenv.get("POLYGON_API_KEY");



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
