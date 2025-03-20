package org.example.util;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A utility class for making API requests to fetch stock data from Polygon.io.
 */
public class ApiClient {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String POLYGON_API_KEY = dotenv.get("POLYGON_API_KEY");
    private static final String[] POLYGON_API_KEYS = {
            dotenv.get("POLYGON_API_KEY_1"),
            dotenv.get("POLYGON_API_KEY_2"),
            dotenv.get("POLYGON_API_KEY_3"),
            dotenv.get("POLYGON_API_KEY_4")
    };
    private static int numberOfRequests = 0;
    private static int numberOfKeys = 1;

    /**
     * Returns the number of API keys available.
     * @return The number of API keys being used.
     */
    public static int getNumberOfApiKeys() {return numberOfKeys;}

    /**
     * Fetches stock data from a given API endpoint.
     *
     * @param apiCall The full API URL to request data from.
     * @return The JSON response as a String.
     * @throws IOException If the request fails or the response code is not 200.
     */
    public static String fetchStockData(String apiCall) throws IOException {
        ApiClient.numberOfRequests++;
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
        } else if (httpResponse == 429) {
            throw new IOException("Maximum number of requests exceeded");
        } else{
            throw new IOException("Http request has failed with the response code: " + httpResponse);
        }
    }

    /**
     * Retrieves general stock data for a given ticker symbol using Polygon.io API.
     *
     * @param tickerSymbol The stock ticker symbol.
     * @return The JSON response containing stock information.
     * @throws IOException If the API request fails.
     */
    public static @NotNull String getGeneralStockData(String tickerSymbol) throws IOException {
        return fetchStockData(String.format("https://api.polygon.io/v3/reference/tickers/%s?apiKey=%s",
                tickerSymbol, getApiKey()
        ));
    }

    /**
     * Retrieves an API key to be used for requests. If a single primary key is available, it is used;
     * otherwise, the method cycles through multiple keys.
     * This is intended for the validation utility, in which it is helpful to have more than 5 Api calls per minute.
     *
     * @return An API key as a String.
     */
    private static String getApiKey(){
        if(dotenv.get("POLYGON_API_KEY") != null){
            numberOfKeys = 1;
            return dotenv.get("POLYGON_API_KEY");
        }
        else {
            numberOfKeys = 4;
            return POLYGON_API_KEYS[numberOfRequests % 4];
        }
    }

}
