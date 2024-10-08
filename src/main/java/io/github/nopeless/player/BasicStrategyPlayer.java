package io.github.nopeless.player;

import io.github.nopeless.sortedlist.SortedList;

import java.util.List;

public class BasicStrategyPlayer implements Player {
    public boolean offeredCard(int cardNumber, int chipsOnCard, List<SortedList<Integer>> playersHands,
                               int myPlayerNum, int myChips) {
        // check if neighboring cards exist
        SortedList<Integer> myHand = playersHands.get(myPlayerNum);
        if (myHand.contains(cardNumber + 1) || myHand.contains(cardNumber - 1))
            return true;

        if (cardNumber >= 33) return false;

        // obvious choice
        if (cardNumber - chipsOnCard <= 0)
            return true;

        // check how early the game is in
        int left = 24 - playersHands.stream().mapToInt(SortedList::size).sum();

        if (left < 3) return false; // avoid at all costs

        if (myHand.size() < 3) {
            if (chipsOnCard > 6 && cardNumber - chipsOnCard / 2 < 20) return true;
            return false;
        }

        return myChips == 0;
    }
}
