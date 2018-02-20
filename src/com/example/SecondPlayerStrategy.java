package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecondPlayerStrategy implements PlayerStrategy {

    public List<RunMeld> runMeldCardsList = new ArrayList<>();
    public List<SetMeld> setMeldCardsList = new ArrayList<>();
    public List<Card> playersHand = new ArrayList<>();

    public List<SetMeld> initializeSetMeld(List<Card> hand) {
        Collections.sort(hand);
        List<List<Card>> cardsBy3List = new ArrayList<>();
        int startingIndex;
        int endingIndex = 3;

        if (hand.size() > 4) {
            for (int handIndex = 0; handIndex < hand.size(); handIndex++) {
                List<Card> cardsBy3 = new ArrayList<>();
                if (endingIndex <= hand.size()) {
                    for (startingIndex = handIndex; startingIndex < endingIndex; startingIndex++) {
                        cardsBy3.add(hand.get(startingIndex));
                    }
                }
                cardsBy3List.add(cardsBy3);
                endingIndex++;
            }
        } else if (hand.size() == 3) {
            cardsBy3List.add(hand);
        } else if (hand.size() == 4) {
            cardsBy3List.add(hand);
        } else {
            return null;
        }
        System.out.println(cardsBy3List.toString());
        for (int i = 0; i < cardsBy3List.size(); i++) {
            if (cardsBy3List.get(i).size() >= SetMeld.MIN_CARDS && cardsBy3List.get(i).size() <= SetMeld.MAX_CARDS) {
                if (Meld.buildSetMeld(cardsBy3List.get(i)) != null) {
                    setMeldCardsList.add(Meld.buildSetMeld(cardsBy3List.get(i)));
                    removeMeldFromPlayerCard(cardsBy3List.get(i));
                    break;
                }
            }
        }
        return setMeldCardsList;
    }

    public List<RunMeld> initializeRunMeld(List<Card> hand) {
        List<List<Card>> sameSuitList = new ArrayList<>();
        Collections.sort(hand);

        List<Card> diamondsSuits = new ArrayList<>();
        List<Card> heartsSuits = new ArrayList<>();
        List<Card> spadesSuit = new ArrayList<>();
        List<Card> clubsSuit = new ArrayList<>();

        for (int handIndex = 0; handIndex < hand.size(); handIndex++) {
            if (hand.get(handIndex).getSuit().ordinal() == 0) {
                diamondsSuits.add(hand.get(handIndex));
            } else if (hand.get(handIndex).getSuit().ordinal() == 1) {
                heartsSuits.add(hand.get(handIndex));
            } else if (hand.get(handIndex).getSuit().ordinal() == 2) {
                spadesSuit.add(hand.get(handIndex));
            } else if (hand.get(handIndex).getSuit().ordinal() == 3) {
                clubsSuit.add(hand.get(handIndex));
            }
        }

        sameSuitList.add(diamondsSuits);
        sameSuitList.add(heartsSuits);
        sameSuitList.add(spadesSuit);
        sameSuitList.add(clubsSuit);

        for (int i = 0; i < sameSuitList.size(); i++) {
            if (sameSuitList.get(i).size() >= RunMeld.MIN_CARDS && sameSuitList.get(i).size() <= RunMeld.MAX_CARDS) {
                if (Meld.buildRunMeld(sameSuitList.get(i)) != null) {
                    runMeldCardsList.add(Meld.buildRunMeld(sameSuitList.get(i)));
                    removeMeldFromPlayerCard(sameSuitList.get(i));
                    break;
                }
            }
        }
        return runMeldCardsList;
    }

    /**
     * Helper method for initiateRunMeld and initiateSetMeld
     * for removing the cards that are part of a meld from the player's hand
     * @param meldCards
     */
    public boolean removeMeldFromPlayerCard(List<Card> meldCards) {
        List<Card> oldPlayerHand = playersHand;

        for (int i = 0; i < meldCards.size(); i++) {
            playersHand.remove(meldCards.get(i));
        }

        List<Card> newPlayerHand = playersHand;
        if (oldPlayerHand.equals(newPlayerHand)) {
            return false;
        }
        return true;
    }

    public void receiveInitialHand(List<Card> hand) {
        playersHand = hand;
        runMeldCardsList = initializeRunMeld(playersHand);
        setMeldCardsList = initializeSetMeld(playersHand);
        if (runMeldCardsList != null || !runMeldCardsList.isEmpty()) {
            for (int handIndex = 0; handIndex < playersHand.size(); handIndex++) {
                if (runMeldCardsList.get(0).canAppendCard(playersHand.get(handIndex))) {
                    runMeldCardsList.get(0).appendCard(playersHand.get(handIndex));
                }
            }
        }
        if (setMeldCardsList != null || !setMeldCardsList.isEmpty()) {
            for (int handIndex = 0; handIndex < playersHand.size(); handIndex++) {
                if (setMeldCardsList.get(0).canAppendCard(playersHand.get(handIndex))) {
                    setMeldCardsList.get(0).appendCard(playersHand.get(handIndex));
                }
            }
        }
    }

    /**
     * Called by the game engine to prompt the player on whether they want to take the top card
     * from the discard pile or from the deck.
     *
     * @param card The card on the top of the discard pile
     * @return whether the user takes the card on the discard pile
     */
    public boolean willTakeTopDiscard(Card card) {
        boolean takeCard = false;
        if (runMeldCardsList != null || !runMeldCardsList.isEmpty()) {
            if (runMeldCardsList.get(0).canAppendCard(card)) {
                //playersHand.add(card);
                takeCard = true;
            }
        } else if (setMeldCardsList != null || !setMeldCardsList.isEmpty()) {
            if (setMeldCardsList.get(0).canAppendCard(card)) {
                //playersHand.add(card);
                takeCard = true;
            }
        }
        return takeCard;
    }

    /**
     * Called by the game engine to prompt the player to take their turn given a
     * dealt card (and returning their card they've chosen to discard).
     *
     * @param drawnCard The card the player was dealt
     * @return The card the player has chosen to discard
     */
    public Card drawAndDiscard(Card drawnCard) {
//        if (runMeldCards.canAppendCard(drawnCard)) {
//            runMeldCards.appendCard(drawnCard);
//            playersHand.add(drawnCard);
//        } else if (setMeldCards.canAppendCard(drawnCard)) {
//            setMeldCards.appendCard(drawnCard);
//            playersHand.add(drawnCard);
//        }
//
//        int meldLength = runMeldCards.getCards().length + setMeldCards.getCards().length;
//
//        for (int handIndex = 0; handIndex < playersHand.size(); handIndex++) {
//            if (runMeldCards.canRemoveCard(runMeldCards.getCards()[handIndex])) {
//                runMeldCards.removeCard(runMeldCards.getCards()[handIndex]);
//                playersHand.remove(playersHand.get(handIndex));
//                break;
//            } else if (setMeldCards.canRemoveCard(setMeldCards.getCards()[handIndex])) {
//                setMeldCards.removeCard(playersHand.get(handIndex));
//                playersHand.remove(playersHand.get(handIndex));
//                break;
//            }
//        }
        return drawnCard;
    }

    /**
     * Called by the game engine to prompt the player is whether they would like to
     * knock.
     *
     * @return True if the player has decided to knock
     */
    public boolean knock() {
        return true;
    }

    /**
     * Called by the game engine when the opponent has finished their turn to provide the player
     * information on what the opponent just did in their turn.
     *
     * @param drewDiscard Whether the opponent took from the discard
     * @param previousDiscardTop What the opponent could have drawn from the discard if they chose to
     * @param opponentDiscarded The card that the opponent discarded
     */
    public void opponentEndTurnFeedback(boolean drewDiscard, Card previousDiscardTop, Card opponentDiscarded) {

    }

    /**
     * Called by the game engine when the round has ended to provide this player strategy
     * information about their opponent's hand and selection of Melds at the end of the round.
     *
     * @param opponentHand The opponent's hand at the end of the round
     * @param opponentMelds The opponent's Melds at the end of the round
     */
    public void opponentEndRoundFeedback(List<Card> opponentHand, List<Meld> opponentMelds) {

    }

    /**
     * Called by the game engine to allow access the player's current list of Melds.
     *
     * @return The player's list of melds.
     */
    public List<Meld> getMelds() {
        Card[] setMeldArr = setMeldCardsList.get(0).getCards();
        Card[] runMeldArr = runMeldCardsList.get(0).getCards();
        List<Meld> allMeld = new ArrayList<>();
        allMeld.add(Meld.buildSetMeld(setMeldArr));
        allMeld.add(Meld.buildRunMeld(runMeldArr));
        return allMeld;
    }

    /**
     * Called by the game engine to allow this player strategy to reset its internal state before
     * competing it against a new opponent.
     */
    public void reset() {

    }
}
