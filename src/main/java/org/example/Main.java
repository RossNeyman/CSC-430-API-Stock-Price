package org.example;

import org.example.util.ApiClient;
import org.example.util.SerDe;
import java.util.Scanner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean quitGame = false;
        int wins = 0;
        while(!quitGame) {
            System.out.println("You've won " + wins + " times.");
            System.out.println("Enter the stock ticker symbol: ");
            String ticker = scanner.nextLine();

            try {
                boolean result = ListDateNameGame.playGame(ticker.toUpperCase());
                if (result) {
                    System.out.println("Congratulations! You won!");
                    wins++;
                } else {
                    System.out.println("Better luck next time!");
                }
                System.out.println(
                        "Would you like to play again?" + '\n'
                        +"1. Play again" + '\n'
                        +"2. Quit"
                );
                if(scanner.nextInt() != 1){
                    quitGame = true;
                }
                scanner.nextLine();
            } catch (IOException e) {
                System.out.println("An error occurred while retrieving stock data.");
                e.printStackTrace();
            }
        }
    }

}