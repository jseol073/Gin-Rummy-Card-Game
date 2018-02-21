package com.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SecondPlayerStrategyTest {

    private List<Card> deck = new ArrayList<>();
    private List<Card> hand = new ArrayList<>();
    private SecondPlayerStrategy player2 = new SecondPlayerStrategy();

    @Before
    public void setUp() throws Exception {
        hand.clear();
        deck.clear();
        deck.addAll(Card.getAllCards());
        Collections.sort(deck);
    }

    @Test
    public void initializeSetMeldTest() {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getRankValue() == 12) {
                hand.add(deck.get(i));
            }
        }
        List<SetMeld> output = new ArrayList<>();
        output.add(Meld.buildSetMeld(hand));
        System.out.println(output.get(0).getCards()[0].getRankValue());
        Card[] expectedCards = output.get(0).getCards();
        Card[] actualCards = player2.initializeSetMeld(hand).get(0).getCards();
        for (int i = 0; i < expectedCards.length; i++) {
            assertSame(expectedCards[i].getRankValue(), actualCards[i].getRankValue());
            assertSame(expectedCards[i].getSuit(), actualCards[i].getSuit());
        }
        //step 1, get first setmeld of each list (each contain only one setmeld)
        //step 2, get card array from each set meld object
        //step 3 for each card in the card array of the expected output, check if the card[] of the actual setMeld obj contains that card
    }

    @Test
    public void initializeSetMeldMoreRankTest() {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getRankValue() == 3) {
                hand.add(deck.get(i));
            }
        }
        List<SetMeld> output = new ArrayList<>();
        output.add(Meld.buildSetMeld(hand));
        System.out.println(output.get(0).getCards()[0].getRankValue());
        Card[] expectedCards = output.get(0).getCards();
        Card[] actualCards = player2.initializeSetMeld(hand).get(0).getCards();
        for (int i = 0; i < expectedCards.length; i++) {
            assertSame(expectedCards[i].getRankValue(), actualCards[i].getRankValue());
            assertSame(expectedCards[i].getSuit(), actualCards[i].getSuit());
        }
    }

    @Test
    public void initializeRunMeldTest() {
        List<Card> onlyHearts = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getSuit().ordinal() == 1) {
                onlyHearts.add(deck.get(i));
            }
        }
        Collections.sort(onlyHearts);
        for (int j = 0; j < 4; j++) {
            hand.add(onlyHearts.get(j));
        }
        List<RunMeld> output = new ArrayList<>();
        output.add(Meld.buildRunMeld(hand));
        System.out.println(output.get(0).getCards()[0].getSuit());

        Card[] expectedCards = output.get(0).getCards();
        Card[] actualCards = player2.initializeRunMeld(hand).get(0).getCards();
        for (int i = 0; i < expectedCards.length; i++) {
            assertSame(expectedCards[i].getRankValue(), actualCards[i].getRankValue());
            assertSame(expectedCards[i].getSuit(), actualCards[i].getSuit());
        }
    }

    @Test
    public void removeMeldFromPlayerCardFalseTest() {
        assertEquals(false, player2.removeMeldFromPlayerCard(hand));
    }

    @Test
    public void removeMeldFromPlayerCardTrueTest() {
        List<Card> onlyHearts = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getSuit().ordinal() == 1) {
                onlyHearts.add(deck.get(i));
            }
        }
        Collections.sort(onlyHearts);
        for (int j = 0; j < 4; j++) {
            hand.add(onlyHearts.get(j));
        }
        System.out.println(hand.toString());
        player2.setDeadWoodCards(deck);
        assertEquals(true, player2.removeMeldFromPlayerCard(hand));
    }

    @Test
    public void willTakeTopDiscardTest() {
        Card input = deck.get(0);

    }

    @Test
    public void drawAndDiscard() {
    }

    @Test
    public void knock() {
    }

    @Test
    public void opponentEndTurnFeedback() {
    }

    @Test
    public void opponentEndRoundFeedback() {
    }

    @Test
    public void getMelds() {
    }

    @Test
    public void reset() {
    }
}