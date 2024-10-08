package io.github.nopeless.player;

import io.github.nopeless.sortedlist.SortedList;

import java.util.List;

public class RandomPlayer implements Player {
    public boolean offeredCard(int cardNumber, int chipsOnCard,
                               List<SortedList<Integer>> playersHands,
                               int myPlayerNum, int myChips) {

        // check if neighboring cards exist
//        SortedList<Integer> myHand = playersHands.get(myPlayerNum);
//        if (myHand.contains(cardNumber + 1) || myHand.contains(cardNumber - 1))
//            return true;

        // If I am out of chips, I must take the card.
        if (myChips == 0)
            return true;

        else {
            // pick a random number from 0 to [# of chips].
            // If the number is zero, then take the card, otherwise reject it.

            int randNum = (int)(Math.random() * myChips);
            if (randNum == 0) {
                return true; // take card
            }
            else {
                return false; // no thanks! (reject card)
            }
        }
    }
}
