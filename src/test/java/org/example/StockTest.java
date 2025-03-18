package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class StockTest {

    @Test
    public void testSetAndGetTicker() {
        Stock stock = new Stock();
        stock.setTicker("AAPL");
        assertEquals("AAPL", stock.getTicker());
    }

    @Test
    public void testSetAndGetName() {
        Stock stock = new Stock();
        stock.setName("Apple Inc.");
        assertEquals("Apple Inc.", stock.getName());
    }

    @Test
    public void testSetAndGetMarket() {
        Stock stock = new Stock();
        stock.setMarket("NASDAQ");
        assertEquals("NASDAQ", stock.getMarket());
    }

    @Test
    public void testSetAndGetPrimaryExchange() {
        Stock stock = new Stock();
        stock.setPrimaryExchange("NASDAQ");
        assertEquals("NASDAQ", stock.getPrimaryExchange());
    }

    @Test
    public void testSetAndGetType() {
        Stock stock = new Stock();
        stock.setType("Common Stock");
        assertEquals("Common Stock", stock.getType());
    }

    @Test
    public void testSetAndGetCurrencyName() {
        Stock stock = new Stock();
        stock.setCurrency_name("USD");
        assertEquals("USD", stock.getCurrencyName());
    }

    @Test
    public void testSetAndGetListDate() {
        Stock stock = new Stock();
        stock.setList_date("1980-12-12");
        assertEquals("1980-12-12", stock.getListDate());
    }
}