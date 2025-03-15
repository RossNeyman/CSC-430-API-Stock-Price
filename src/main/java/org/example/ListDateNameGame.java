package org.example;
import org.example.util.ApiClient;
import org.example.util.SerDe;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ListDateNameGame {
    private String stockTicker;
    private final int EASY_DATE_GAME_DIFFICULTY = 2;
    private final int MEDIUM_DATE_GAME_DIFFICULTY = 4;
    private final int HARD_DATE_GAME_DIFFICULTY = 10;


    private String[] generateRandomDates(String listDate, int numberOfDates){
        String[] dates = new String[numberOfDates];
        int trueYear,trueMonth, trueDay, trueIndex;

        LocalDate trueListDate = LocalDate.parse(listDate);
        trueYear = trueListDate.getYear();
        trueMonth = trueListDate.getMonthValue();
        trueDay = trueListDate.getDayOfMonth();
        trueIndex = ThreadLocalRandom.current().nextInt(numberOfDates);
        dates[trueIndex] = listDate;

        for(int i = 0; i < numberOfDates; i++){
            LocalDate startDate = LocalDate.of(1900, 1, 1);
            LocalDate endDate = LocalDate.of(2025, 1, 1);
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            long randomDayOffset = ThreadLocalRandom.current().nextLong(daysBetween + 1);
            LocalDate randomDate = startDate.plusDays(randomDayOffset);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if(i == trueIndex && trueIndex != numberOfDates -1){
                dates[i+1] = randomDate.format(dateFormatter);
            }
            else if(i != trueIndex){
                dates[i] = randomDate.format(dateFormatter);
            }
        }
        return dates;
    }


    public void playGame() throws IOException {

    }



}
