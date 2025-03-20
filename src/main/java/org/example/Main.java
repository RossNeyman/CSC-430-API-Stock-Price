package org.example;

import org.example.util.ApiClient;
import org.example.util.SerDe;
import java.util.Scanner;


import java.io.IOException;

public class Main {
    private static final String TICKER_SYMBOLS_FILE = "tickers/top100tickers";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quitGame = false;
        int wins = 0;
        int streak = 0;

        try {
            TickerSymbolProvider tickerProvider = new TickerSymbolProvider(TICKER_SYMBOLS_FILE);

            while(!quitGame) {
                System.out.println("You've won " + wins + " times.");
                if(streak > 1) {
                    System.out.println("You're on a " + streak + " win streak!");
                }

                String ticker;
                ticker = tickerProvider.getRandomTickerSymbol();
                System.out.println("Random ticker selected: " + ticker);

                try {
                    boolean result = ListDateNameGame.playGame(ticker);
                    if (result) {
                        wins++;
                        streak++;
                    }
                    else{
                        streak = 0;
                    }

                    System.out.println("Would you like to play again?");
                    System.out.println("1. Play again");
                    System.out.println("2. Quit");

                    if(getIntInput(scanner) != 1) {
                        quitGame = true;
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while retrieving stock data.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load ticker symbols: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static int getIntInput(Scanner scanner) {
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return input;
    }
}