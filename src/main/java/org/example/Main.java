package org.example;

import org.example.util.ApiClient;
import org.example.util.SerDe;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ListDateNameGame game = new ListDateNameGame();
        game.playGame();
    }
}