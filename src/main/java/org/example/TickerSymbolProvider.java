package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TickerSymbolProvider {
    private final List<String> tickerSymbols;
    private final Random random = new Random();

    public TickerSymbolProvider(String resourcePath) throws IOException {
        tickerSymbols = loadTickerSymbols(resourcePath);
    }

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

    public String getRandomTickerSymbol() {
        if (tickerSymbols.isEmpty()) {
            throw new IllegalStateException("No ticker symbols available");
        }

        int randomIndex = random.nextInt(tickerSymbols.size());
        return tickerSymbols.get(randomIndex);
    }

    public List<String> getAllTickerSymbols() {
        return new ArrayList<>(tickerSymbols);
    }
}