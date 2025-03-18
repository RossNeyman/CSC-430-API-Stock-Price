package org.example;

import org.example.util.ApiClient;
import org.example.util.SerDe;
import java.util.Scanner;

import java.io.IOException;

public class Main {
    private static final String TICKER_SYMBOLS_FILE = "tickers/top100tickers.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quitGame = false;
        int wins = 0;

        try {
            TickerSymbolProvider tickerProvider = new TickerSymbolProvider("tickers/top100tickers.txt");

            while(!quitGame) {
                System.out.println("You've won " + wins + " times.");

                String ticker;
                System.out.println("Select an option:");
                System.out.println("1. Enter a ticker symbol manually");
                System.out.println("2. Use a random popular ticker");

                int choice = getIntInput(scanner);

                if (choice == 1) {
                    System.out.println("Enter the stock ticker symbol: ");
                    ticker = scanner.nextLine().toUpperCase();
                } else {
                    ticker = tickerProvider.getRandomTickerSymbol();
                    System.out.println("Random ticker selected: " + ticker);
                }

                try {
                    boolean result = ListDateNameGame.playGame(ticker);
                    if (result) {
                        System.out.println("Congratulations! You won!");
                        wins++;
                    } else {
                        System.out.println("Better luck next time!");
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