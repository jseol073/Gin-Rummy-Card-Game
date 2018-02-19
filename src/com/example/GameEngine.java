package com.example;

import java.util.*;

public class GameEngine {

    private FirstPlayerStrategy strategy1;
    private SecondPlayerStrategy strategy2;
    private List<Card> deck = new ArrayList<>();
    private List<Card> discardDeck = new ArrayList<>();

    /**
     * gives ten cards to each player and then removes those cards from the deck
     * @param deck
     * @return ten cards to the player
     */
    public List<Card> giveInitialHand(List<Card> deck) {
        List<Card> playersCardList = new ArrayList<>();
        Collections.shuffle(deck);
        for (int i = 0; i < 10; i++) {
            playersCardList.add(deck.get(i));
        }
        deck.remove(playersCardList);
        return playersCardList;
    }

    public GameEngine(FirstPlayerStrategy strategy1, SecondPlayerStrategy strategy2) {
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }

    public void runGame() {
        deck.addAll(Card.getAllCards());
        boolean isDeckNotEmpty = true;
        int player1Points = 0;
        int player2Points = 0;
        while (isDeckNotEmpty) {
            if (deck.size() <= 0) {
                isDeckNotEmpty = false;
            }

            discardDeck.add(deck.remove(0)); //removes first card from deck and adds it to discard pile each iteration
            List<Card> player1Cards = giveInitialHand(deck);
            List<Card> player2Cards = giveInitialHand(deck);
            strategy1.receiveInitialHand(player1Cards);
            strategy2.receiveInitialHand(player2Cards);

            //drawing and discarding cards:
            if (strategy1.willTakeTopDiscard(discardDeck.get(0))) {
                if (strategy1.drawAndDiscard(discardDeck.get(0)) != null) {
                    player1Cards.add(discardDeck.get(0));
                    player1Cards.remove(strategy1.drawAndDiscard(discardDeck.remove(0)));
                } else {
                    System.out.println("player 1 did not draw or discard.");
                }

            } else {
                if (strategy2.willTakeTopDiscard(discardDeck.get(0))) {
                    if (strategy2.drawAndDiscard(discardDeck.get(0)) != null) {
                        player2Cards.add(discardDeck.get(0));
                        player2Cards.remove(strategy2.drawAndDiscard(discardDeck.remove(0)));
                    } else {
                        System.out.println("player 2 did not draw or discard.");
                    }
                } else {
                    if (strategy1.willTakeTopDiscard(deck.get(0))) {
                        if (strategy1.drawAndDiscard(discardDeck.get(0)) != null) {
                            player1Cards.add(deck.get(0));
                            player1Cards.remove(strategy1.drawAndDiscard(discardDeck.get(0)));
                        }
                    }
                }
            }

            //Knock:
            if (strategy1.knock()) {

            } else {

            }


        }
    }

    public void removeCardsFromDeck(List<Card> deck) {

    }



}
