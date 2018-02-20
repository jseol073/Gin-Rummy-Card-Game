package com.example;

import java.util.*;

public class GameEngine {

    private FirstPlayerStrategy strategy1;
    private SecondPlayerStrategy strategy2;
    public List<Card> deck = new ArrayList<>();
    private List<Card> discardPile = new ArrayList<>();

    /**
     * gives ten cards to each player and then removes those cards from the deck
     * @param mainDeck
     * @return ten cards to the player
     */
    public List<Card> giveInitialHand(List<Card> mainDeck) {
        List<Card> playersCardList = new ArrayList<>();
        Collections.shuffle(mainDeck);
        for (int i = 0; i < 10; i++) {
            playersCardList.add(mainDeck.get(i));
        }
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
        while (isDeckNotEmpty && (player1Points < 50 || player2Points < 50)) {
            if (deck.size() <= 0) {
                isDeckNotEmpty = false;
            }

            List<Card> player1Cards = giveInitialHand(deck);
            deck.remove(player1Cards);
            List<Card> player2Cards = giveInitialHand(deck);
            deck.remove(player2Cards);
            strategy1.receiveInitialHand(player1Cards);
            strategy2.receiveInitialHand(player2Cards);

            discardPile.add(deck.remove(0)); //removes first card from deck and adds it to discard pile for each iteration

            //drawing and discarding cards:
            if (strategy1.willTakeTopDiscard(discardPile.get(0))) {
                if (strategy1.drawAndDiscard(discardPile.get(0)) != null) {
                    player1Cards.add(discardPile.get(0));
                    discardPile.add(strategy1.drawAndDiscard(discardPile.get(0)));
                    player1Cards.remove(strategy1.drawAndDiscard(discardPile.remove(0)));
                } else {
                    System.out.println("player 1's discard card is null.");
                }

            } else if (strategy2.willTakeTopDiscard(discardPile.get(0))) {
                if (strategy2.drawAndDiscard(discardPile.get(0)) != null) {
                    player2Cards.add(discardPile.get(0));
                    discardPile.add(strategy2.drawAndDiscard(discardPile.get(0)));
                    player2Cards.remove(strategy2.drawAndDiscard(discardPile.remove(0)));
                } else {
                    System.out.println("player 2's discard card is null.");
                }

            } else if (strategy1.willTakeTopDiscard(deck.get(0))) {
                if (strategy1.drawAndDiscard(deck.get(0)) != null) {
                    player1Cards.add(deck.get(0));
                    discardPile.add(strategy1.drawAndDiscard(deck.get(0)));
                    player1Cards.remove(strategy1.drawAndDiscard(deck.remove(0)));
                } else {
                    System.out.println("player 1's discard card is null.");
                }

            } else if (strategy2.willTakeTopDiscard(deck.get(0))) {
                if (strategy2.drawAndDiscard(deck.get(0)) != null) {
                    player1Cards.add(deck.get(0));
                    discardPile.add(strategy2.drawAndDiscard(deck.get(0)));
                    player1Cards.remove(strategy2.drawAndDiscard(deck.remove(0)));
                } else {
                    System.out.println("player 2's discard card is null.");
                }
            }

            //Knock:
            if (strategy1.knock()) {

            } else if (strategy2.knock()) {

            }


        }
    }

    public void removeCardsFromDeck(List<Card> deck) {

    }



}
