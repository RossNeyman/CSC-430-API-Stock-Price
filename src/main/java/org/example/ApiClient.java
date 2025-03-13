package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final String POLYGON_API_KEY = "l3jdNtAyS1BLUEN3wDeSsp2yzxYMbm4t";

    //public static final String TEST_API_URL = "https://api.polygon.io/v2/aggs/ticker/AAPL/range/2/day/2023-01-09/2023-02-10?adjusted=true&sort=asc&apiKey=l3jdNtAyS1BLUEN3wDeSsp2yzxYMbm4t";

    public static String fetchRealTimeStockPrice(String tickerSymbol) throws IOException {
        String apiCall = String.format("https://api.polygon.io/v2/last/trade/%s?apiKey=%s",
                tickerSymbol, POLYGON_API_KEY
        );
        URL apiCallUrl = new URL(apiCall);
        HttpURLConnection apiConnection = (HttpURLConnection) apiCallUrl.openConnection();

        int httpResponse = apiConnection.getResponseCode();
        if(httpResponse == 200){

        }
        else{
            throw new IOException("Http request has failed with the response code: " + httpResponse);
        }

    }
}
