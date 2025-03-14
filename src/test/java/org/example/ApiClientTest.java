package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiClientTest {

    @Test
    void testFetchStockData_Success() throws IOException {
        // Arrange
        String expectedJson = "{\"status\":\"OK\",\"results\":{\"price\":150.82,\"timestamp\":1641196800000}}";
        String testUrl = "https://api.polygon.io/v2/test?apiKey=someKey";

        // Create a mock HttpURLConnection that we can control
        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class, Mockito.CALLS_REAL_METHODS);

        // Setup the mock connection behavior
        when(mockConnection.getResponseCode()).thenReturn(200);
        InputStream inputStream = new ByteArrayInputStream(expectedJson.getBytes());
        when(mockConnection.getInputStream()).thenReturn(inputStream);

        // Create a custom URL handler that returns our mock connection
        URLStreamHandler handler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url) {
                return mockConnection;
            }
        };

        // Create a real URL with our custom handler
        URL url = new URL(null, testUrl, handler);

        try (MockedStatic<URL> mockedUrl = Mockito.mockStatic(URL.class)) {
            // Mock the URL constructor to return our custom URL
            mockedUrl.when(() -> new URL(testUrl)).thenReturn(url);

            // Act
            String result = ApiClient.fetchStockData(testUrl);

            // Assert
            assertEquals(expectedJson, result);
            verify(mockConnection).getResponseCode();
            verify(mockConnection).getInputStream();
        }
    }

    @Test
    void testFetchStockData_HttpError() throws IOException {
        // Arrange
        String testUrl = "https://api.polygon.io/v2/test?apiKey=someKey";

        // Create a mock HttpURLConnection that we can control
        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class, Mockito.CALLS_REAL_METHODS);

        // Setup the mock connection to return an error
        when(mockConnection.getResponseCode()).thenReturn(401);

        // Create a custom URL handler that returns our mock connection
        URLStreamHandler handler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url) {
                return mockConnection;
            }
        };

        // Create a real URL with our custom handler
        URL url = new URL(null, testUrl, handler);

        try (MockedStatic<URL> mockedUrl = Mockito.mockStatic(URL.class)) {
            // Mock the URL constructor to return our custom URL
            mockedUrl.when(() -> new URL(testUrl)).thenReturn(url);

            // Act & Assert
            IOException exception = assertThrows(IOException.class, () -> {
                ApiClient.fetchStockData(testUrl);
            });

            assertTrue(exception.getMessage().contains("Http request has failed"));
            verify(mockConnection).getResponseCode();
            verify(mockConnection, never()).getInputStream();
        }
    }

    @Test
    void testGetRealTimeStockData_Success() throws IOException {
        // Arrange
        String expectedJson = "{\"status\":\"success\",\"results\":{\"price\":151.23,\"timestamp\":1641196800000}}";
        String ticker = "AAPL";
        String expectedUrl = "https://api.polygon.io/v2/last/trade/AAPL?apiKey=l3jdNtAyS1BLUEN3wDeSsp2yzxYMbm4t";

        // Create a mock HttpURLConnection that we can control
        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class, Mockito.CALLS_REAL_METHODS);

        // Setup the mock connection behavior
        when(mockConnection.getResponseCode()).thenReturn(200);
        InputStream inputStream = new ByteArrayInputStream(expectedJson.getBytes());
        when(mockConnection.getInputStream()).thenReturn(inputStream);

        // Create a custom URL handler that returns our mock connection
        URLStreamHandler handler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url) {
                return mockConnection;
            }
        };

        // Use a spy to verify the URL construction is correct
        try (MockedStatic<ApiClient> spyApiClient = Mockito.mockStatic(ApiClient.class, Mockito.CALLS_REAL_METHODS)) {
            // Create a URL spy
            URL urlSpy = spy(new URL(null, expectedUrl, handler));

            try (MockedStatic<URL> mockedUrl = Mockito.mockStatic(URL.class)) {
                // Mock the URL constructor to return our URL spy
                mockedUrl.when(() -> new URL(expectedUrl)).thenReturn(urlSpy);

                // Allow the original getRealTimeStockData method to run, but make fetchStockData return our expected JSON
                spyApiClient.when(() -> ApiClient.fetchStockData(expectedUrl)).thenReturn(expectedJson);

                // Act
                String result = ApiClient.getRealTimeStockData(ticker);

                // Assert
                assertEquals(expectedJson, result);
                spyApiClient.verify(() -> ApiClient.fetchStockData(expectedUrl), times(1));
            }
        }
    }
}