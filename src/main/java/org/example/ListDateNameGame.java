package org.example;
import org.example.util.ApiClient;
import org.example.util.SerDe;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class ListDateNameGame {
    private String stockTicker;

    /**
     * Generates an array of random dates with one correct listing date.
     *
     * @param listDate The correct listing date of the stock in "yyyy-MM-dd" format.
     * @param numberOfDates The number of dates to generate.
     * @return An array of unique dates including the correct listing date.
     */
    String[] generateRandomDates(String listDate, int numberOfDates){
        String[] dates = new String[numberOfDates];
        int trueYear,trueMonth, trueDay, trueIndex;

        LocalDate trueListDate = LocalDate.parse(listDate);
        trueIndex = ThreadLocalRandom.current().nextInt(numberOfDates);
        dates[trueIndex] = listDate;

        List<LocalDate> dateRange = createDateRange(trueListDate);
        LocalDate startDate = dateRange.get(0);
        LocalDate endDate = dateRange.get(1);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        do {
            for (int i = 0; i < numberOfDates; i++) {
                long randomDayOffset = ThreadLocalRandom.current().nextLong(daysBetween + 1);
                LocalDate randomDate = startDate.plusDays(randomDayOffset);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if (i == trueIndex && trueIndex != numberOfDates - 1) {
                    dates[i + 1] = randomDate.format(dateFormatter);
                } else if (i != trueIndex) {
                    dates[i] = randomDate.format(dateFormatter);
                }
            }
        }while(hasDuplicateDates(dates));
        return dates;
    }

    /**
     * Plays the stock listing date guessing game.
     *
     * @param ticker The stock ticker symbol.
     * @return True if the user guessed correctly, false otherwise.
     * @throws IOException If there is an issue retrieving stock data.
     */
    public static boolean playGame(String ticker) throws IOException {
        Stock gameStock = SerDe.deserializeJsonStockResponse(ApiClient.getGeneralStockData(ticker));
        assert gameStock != null;
        boolean correct;

        Scanner gameScanner = new Scanner(System.in);
        String correctListDate = gameStock.getListDate();

        ListDateNameGame game = new ListDateNameGame();
        String[] options = game.generateRandomDates(correctListDate, 4);
        System.out.println("You got " + ticker + ": " + gameStock.getName());
        if(gameStock.getDescription() != null){
            System.out.println(gameStock.getDescription());
        }
        System.out.println("Guess the correct listing date for the stock: " + ticker);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter the number corresponding to your choice: ");
        int userChoice = gameScanner.nextInt();
        if(options[userChoice - 1].equals(correctListDate)){
            correct = true;
            System.out.println("You got correct listing date for the stock: " + ticker);
        }
        else{
            correct = false;
            System.out.println("You got incorrect listing date for the stock: " + ticker);
            System.out.println("The correct choice was " + gameStock.getListDate());
        }
        return correct;
    }

    /**
     * Creates a date range 20 years before and after the given date.
     * Sets the end date to the current date if the given date was less than 20 years ago
     *
     * @param correctListDate The stock's correct listing date.
     * @return A list containing the start and end dates of the range.
     */
    List<LocalDate> createDateRange(LocalDate correctListDate){
        LocalDate startDate = LocalDate.of(correctListDate.getYear()-20, 1, 1);
        LocalDate endDate = LocalDate.of(correctListDate.getYear()+20, 1, 1);
        if(endDate.isAfter(LocalDate.now())){
            endDate = LocalDate.now();
        }
        return new ArrayList<>(Arrays.asList(startDate, endDate));
    }

    /**
     * Checks whether an array of dates contains duplicate values.
     *
     * @param dates The array of dates to check.
     * @return True if there are duplicate dates, false otherwise.
     */
    boolean hasDuplicateDates(String[] dates){
        HashSet<String> existingDates = new HashSet<>(Arrays.asList(dates));
        return existingDates.size() < dates.length;
    }
}

