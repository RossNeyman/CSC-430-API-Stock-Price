package org.example;
import org.example.util.ApiClient;
import org.example.util.SerDe;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ListDateNameGame {
    private String stockTicker;

    private String[] generateRandomDates(String listDate, int numberOfDates){
        String[] dates = new String[numberOfDates];
        int trueYear,trueMonth, trueDay, trueIndex;

        LocalDate trueListDate = LocalDate.parse(listDate);
        trueIndex = ThreadLocalRandom.current().nextInt(numberOfDates);
        dates[trueIndex] = listDate;

        for(int i = 0; i < numberOfDates; i++){
            LocalDate startDate = LocalDate.of(1960, 1, 1);
            LocalDate endDate = LocalDate.of(2000, 1, 1);
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

    public static boolean playGame(String ticker) throws IOException {
        Stock gameStock = SerDe.deserializeJsonStockResponse(ApiClient.getGeneralStockData(ticker));
        assert gameStock != null;

        Scanner gameScanner = new Scanner(System.in);
        String correctListDate = gameStock.getListDate();

        ListDateNameGame game = new ListDateNameGame();
        String[] options = game.generateRandomDates(correctListDate, 4);
        System.out.println("You chose " + ticker + ": " + gameStock.getName());
        System.out.println("Guess the correct listing date for the stock: " + ticker);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter the number corresponding to your choice: ");
        int userChoice = gameScanner.nextInt();

        return options[userChoice - 1].equals(correctListDate);
    }

}

