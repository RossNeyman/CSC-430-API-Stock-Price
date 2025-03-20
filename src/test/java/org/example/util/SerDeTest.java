package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SerDeTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testDeserializeJsonStockResponse() {
        String json = "{\"results\": {\"ticker\": \"AAPL\", \"list_date\": \"1980-12-12\"}}";
        Stock stock = SerDe.deserializeJsonStockResponse(json);

        assertNotNull(stock, "Stock object should not be null");
        assertEquals("AAPL", stock.getTicker(), "Stock symbol should match");
        assertEquals("1980-12-12", stock.getListDate(), "Stock price should match");
    }

    @Test
    void testDeserializeJsonStockResponseWithInvalidJson() {
        String invalidJson = "{\"invalid\": \"data\"}";
        Stock stock = SerDe.deserializeJsonStockResponse(invalidJson);
        assertNull(stock, "Stock object should be null for invalid JSON");
    }
}
