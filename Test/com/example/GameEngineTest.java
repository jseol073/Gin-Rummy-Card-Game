package com.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GameEngineTest {
    private List<Card> deck = new ArrayList<>();
    private FirstPlayerStrategy strategy1 = new FirstPlayerStrategy();
    private SecondPlayerStrategy strategy2 = new SecondPlayerStrategy();
    private GameEngine ge = new GameEngine(strategy1, strategy2);

    @Before
    public void setUp() throws Exception {
        deck.clear();
        deck.addAll(Card.getAllCards());
        Collections.sort(deck);
    }

    @Test
    public void giveInitialHandTest() {
        List<Card> input = deck;
        List<Card> expected = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            expected.add(deck.get(i));
        }
        assertEquals(expected, ge.giveInitialHand(input));
    }
}