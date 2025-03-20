package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Reads ticker symbol file and returns as a list
 */
public class TickerSymbolProvider {
    private final List<String> tickerSymbols;
    private final Random random = new Random();

    /**
     * Constructs a TickerSymbolProvider by loading ticker symbols from a resource file.
     *
     * @param resourcePath The path to the resource file containing ticker symbols.
     * @throws IOException If an error occurs while reading the file.
     */
    public TickerSymbolProvider(String resourcePath) throws IOException {
        tickerSymbols = loadTickerSymbols(resourcePath);
    }

    /**
     * Loads ticker symbols from the specified resource file.
     *
     * @param resourcePath The path to the resource file.
     * @return A list of ticker symbols.
     * @throws IOException If an error occurs while reading the file.
     */
    private List<String> loadTickerSymbols(String resourcePath) throws IOException {
        List<String> symbols = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    symbols.add(line.trim());
                }
            }
        }

        return symbols;
    }

    /**
     * Returns a random ticker symbol from the list.
     *
     * @return A randomly selected ticker symbol.
     * @throws IllegalStateException If no ticker symbols are available.
     */
    public String getRandomTickerSymbol() {
        if (tickerSymbols.isEmpty()) {
            throw new IllegalStateException("No ticker symbols available");
        }

        int randomIndex = random.nextInt(tickerSymbols.size());
        return tickerSymbols.get(randomIndex);
    }

    /**
     * Returns all loaded ticker symbols.
     *
     * @return A list of all ticker symbols.
     */
    public List<String> getAllTickerSymbols() {
        return new ArrayList<>(tickerSymbols);
    }
}