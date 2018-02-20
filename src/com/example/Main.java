package com.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FirstPlayerStrategy player1 = new FirstPlayerStrategy();
        SecondPlayerStrategy player2 = new SecondPlayerStrategy();
        GameEngine ge = new GameEngine(player1, player2);
        ge.runGame();
    }
}
