package io.github.nopeless.player;


import io.github.nopeless.sortedlist.SortedList;

import java.util.List;

public class AlwaysRejectPlayer implements Player {
    public boolean offeredCard(int cardNumber, int chipsOnCard,
                               List<SortedList<Integer>> playersHands,
                               int myPlayerNum, int myChips) {


        SortedList<Integer> myHand = playersHands.get(myPlayerNum);
        if (myHand.contains(cardNumber + 1) || myHand.contains(cardNumber - 1))
            return true;

        return false; // reject the card
    }
}
