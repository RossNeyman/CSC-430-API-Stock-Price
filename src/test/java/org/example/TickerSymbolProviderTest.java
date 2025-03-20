package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;;

class TickerSymbolProviderTest {
    private TickerSymbolProvider tickerSymbolProvider;

    @BeforeEach
    void setUp() throws IOException {
        // Using a test resource file named "test_tickers.txt"
        tickerSymbolProvider = new TickerSymbolProvider("tickers/top100tickers");
    }

    @Test
    void testGetAllTickerSymbols() {
        List<String> symbols = tickerSymbolProvider.getAllTickerSymbols();
        assertNotNull(symbols, "Ticker symbols list should not be null");
        assertFalse(symbols.isEmpty(), "Ticker symbols list should not be empty");
    }

    @Test
    void testGetRandomTickerSymbol() {
        String randomSymbol = tickerSymbolProvider.getRandomTickerSymbol();
        assertNotNull(randomSymbol, "Random ticker symbol should not be null");
        assertTrue(tickerSymbolProvider.getAllTickerSymbols().contains(randomSymbol), "Random symbol should be in the list");
    }

    @Test
    void testGetRandomTickerSymbolThrowsExceptionIfEmpty() throws IOException {
        TickerSymbolProvider emptyProvider = new TickerSymbolProvider("tickersTest/emptyTickersForTest");

        assertThrows(IllegalStateException.class, emptyProvider::getRandomTickerSymbol,
                "Should throw exception when no symbols are available");
    }
}
