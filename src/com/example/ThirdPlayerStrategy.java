package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThirdPlayerStrategy {
    private List<RunMeld> runMeldCardsList = new ArrayList<>();
    private List<SetMeld> setMeldCardsList = new ArrayList<>();
    private List<Card> deadWoodCards = new ArrayList<>();

    /**
     * takes 3 cards from the input and checks if it is a setMeld by using buildSetMeld method
     * @param notRunMeldCards
     * @return A list of SetMeld objects
     */
    public List<SetMeld> initializeSetMeld(List<Card> notRunMeldCards) {
        Collections.sort(notRunMeldCards);
        List<List<Card>> cardsBy3List = new ArrayList<>();
        int startingIndex;
        int endingIndex = 3;

        if (notRunMeldCards.size() > 4) {
            for (int handIndex = 0; handIndex < notRunMeldCards.size(); handIndex++) {
                List<Card> cardsBy3 = new ArrayList<>();
                if (endingIndex <= notRunMeldCards.size()) {
                    for (startingIndex = handIndex; startingIndex < endingIndex; startingIndex++) {
                        cardsBy3.add(notRunMeldCards.get(startingIndex));
                    }
                }
                cardsBy3List.add(cardsBy3);
                endingIndex++;
            }
        } else if (notRunMeldCards.size() == 3) {
            cardsBy3List.add(notRunMeldCards);
        } else if (notRunMeldCards.size() == 4) {
            cardsBy3List.add(notRunMeldCards);
        } else {
            return new ArrayList<>();
        }
        for (int i = 0; i < cardsBy3List.size(); i++) {
            if (cardsBy3List.get(i).size() >= SetMeld.MIN_CARDS && cardsBy3List.get(i).size() <= SetMeld.MAX_CARDS) {
                if (Meld.buildSetMeld(cardsBy3List.get(i)) != null) {
                    setMeldCardsList.add(Meld.buildSetMeld(cardsBy3List.get(i)));
                    removeMeldFromPlayerCard(cardsBy3List.get(i));
                    i += 2;
                }
            }
        }
        return setMeldCardsList;
    }

    /**
     * sorts the hand to types of suit and checks if there is a runMeld in each suit
     * @param hand, the player's initial hand
     * @return a list of RunMeld objects
     */
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
                }
            }
        }
        return runMeldCardsList;
    }

    /**
     * Helper method for initiateRunMeld and initiateSetMeld
     * for removing the cards that are part of a meld from the player's hand
     * @param meldCards
     * @return boolean for testing to see if deadWoodCards will change
     */
    public boolean removeMeldFromPlayerCard(List<Card> meldCards) {
        List<Card> oldPlayerHand = deadWoodCards;

        for (int i = 0; i < meldCards.size(); i++) {
            deadWoodCards.remove(meldCards.get(i));
        }

        List<Card> newPlayerHand = deadWoodCards;
        if (oldPlayerHand.equals(newPlayerHand)) {
            return false;
        }
        return true;
    }

    /**
     * Takes the player's cards that are not sorted into a type of meld and see if those cards can be added to
     * one of the melds that were already establised
     * @param hand The initial hand dealt to the player
     */
    public void receiveInitialHand(List<Card> hand) {
        deadWoodCards = hand;
        runMeldCardsList = initializeRunMeld(deadWoodCards);
        setMeldCardsList = initializeSetMeld(deadWoodCards);
        if (!runMeldCardsList.isEmpty()) {
            for (int meldIndex = 0; meldIndex < runMeldCardsList.size(); meldIndex++) {
                for (int handIndex = 0; handIndex < deadWoodCards.size(); handIndex++) {
                    if (runMeldCardsList.get(meldIndex).canAppendCard(deadWoodCards.get(handIndex))) {
                        runMeldCardsList.get(meldIndex).appendCard(deadWoodCards.get(handIndex));
                    }
                }
            }
        }

        if (!setMeldCardsList.isEmpty()) {
            for (int meldIndex = 0; meldIndex < setMeldCardsList.size(); meldIndex++) {
                for (int handIndex = 0; handIndex < deadWoodCards.size(); handIndex++) {
                    if (setMeldCardsList.get(meldIndex).canAppendCard(deadWoodCards.get(handIndex))) {
                        setMeldCardsList.get(meldIndex).appendCard(deadWoodCards.get(handIndex));
                    }
                }
            }
        }
    }

    /**
     * this strategy takes card if only runMelds is not empty and that the card can be appended to a runMelds
     *
     * @param card The card on the top of the discard pile or deck
     * @return true or false if this strategy takes the card
     */
    public boolean willTakeTopDiscard(Card card) {
        boolean takeCard = false;
        if (!runMeldCardsList.isEmpty()) {
            for (int meldIndex = 0; meldIndex < runMeldCardsList.size(); meldIndex++) {
                if (runMeldCardsList.get(meldIndex).canAppendCard(card)) {
                    takeCard = true;
                }
            }
        }
        return takeCard;
    }

    /**
     * This strategy will only append the drawn card to a runMeld if it is possible
     * @param drawnCard The card the player was dealt
     * @return The first card of the deadWoodCards
     */
    public Card drawAndDiscard(Card drawnCard) {
        if (!runMeldCardsList.isEmpty()) {
            if (willTakeTopDiscard(drawnCard)) {
                for (int meldIndex = 0; meldIndex < runMeldCardsList.size(); meldIndex++) {
                    runMeldCardsList.get(meldIndex).appendCard(drawnCard);
                }
            }
        }
        return deadWoodCards.get(0);
    }

    /**
     * Knock if player has some type of meld and some deadwood cards defined by its bounds
     *
     * @return True if the player has decided to knock
     */
    public boolean knock() {
        if (!runMeldCardsList.isEmpty() || !setMeldCardsList.isEmpty()) {
            if (deadWoodCards.size() > 0 && deadWoodCards.size() < 10) {
                return true;
            }
        }
        return false;
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
     * Takes runMeldCardsList and setMeldCardsList and adds it to a list of Meld objects
     *
     * @return The player's list of melds.
     */
    public List<Meld> getMelds() {
        List<Meld> allMeld = new ArrayList<>();
        for (int runIndex = 0; runIndex < runMeldCardsList.size(); runIndex++) {
            allMeld.add(runMeldCardsList.get(runIndex));
        }
        for (int setIndex = 0; setIndex < setMeldCardsList.size(); setIndex++) {
            allMeld.add(setMeldCardsList.get(setIndex));
        }
        return allMeld;
    }

    /**
     * Called by the game engine to allow this player strategy to reset its internal state before
     * competing it against a new opponent.
     */
    public void reset() {
        runMeldCardsList.clear();
        setMeldCardsList.clear();
        deadWoodCards.clear();
    }
}
