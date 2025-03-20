package org.example;

import org.example.util.ApiClient;
import org.example.util.SerDe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListDateNameGameTest {
    private ListDateNameGame game;

    @BeforeEach
    void setUp() {
        game = new ListDateNameGame();
    }

    @Test
    void testGenerateRandomDates() {
        String listDate = "2020-01-01";
        String[] dates = game.generateRandomDates(listDate, 4);

        assertNotNull(dates, "Generated dates should not be null");
        assertEquals(4, dates.length, "Should generate the correct number of dates");
        assertTrue(Arrays.asList(dates).contains(listDate), "Should contain the correct listing date");
        assertEquals(new HashSet<>(Arrays.asList(dates)).size(), dates.length, "Dates should be unique");
    }

    @Test
    void testCreateDateRange() {
        LocalDate correctListDate = LocalDate.of(2020, 1, 1);
        List<LocalDate> range = game.createDateRange(correctListDate);

        assertEquals(2, range.size(), "Date range should contain exactly two elements");
        assertEquals(LocalDate.of(2000, 1, 1), range.get(0), "Start date should be 20 years before");
        assertEquals(LocalDate.now(), range.get(1), "End date should be current date");
    }

    @Test
    void testHasDuplicateDates() {
        String[] uniqueDates = {"2020-01-01", "2021-01-01", "2022-01-01", "2023-01-01"};
        String[] duplicateDates = {"2020-01-01", "2020-01-01", "2021-01-01", "2022-01-01"};

        assertFalse(game.hasDuplicateDates(uniqueDates), "Unique dates should return false");
        assertTrue(game.hasDuplicateDates(duplicateDates), "Duplicate dates should return true");
    }

    @Test
    void testPlayGame() throws IOException {
        try (MockedStatic<ApiClient> mockedApiClient = mockStatic(ApiClient.class);
             MockedStatic<SerDe> mockedSerDe = mockStatic(SerDe.class)) {

            Stock mockStock = mock(Stock.class);
            when(mockStock.getListDate()).thenReturn("2020-01-01");
            when(mockStock.getName()).thenReturn("Mock Stock");
            mockedApiClient.when(() -> ApiClient.getGeneralStockData(anyString())).thenReturn("mocked JSON");
            mockedSerDe.when(() -> SerDe.deserializeJsonStockResponse(anyString())).thenReturn(mockStock);

            boolean result = ListDateNameGame.playGame("MOCK");
            assertFalse(result, "playGame should return false for incorrect user input simulation");
        }
    }
}
