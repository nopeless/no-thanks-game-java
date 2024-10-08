package io.github.nopeless.player;


import io.github.nopeless.sortedlist.SortedList;

import java.util.List;

public class AlwaysTakePlayer implements Player {
    public boolean offeredCard(int cardNumber, int chipsOnCard, List<SortedList<Integer>> playersHands,
                               int myPlayerNum, int myChips) {
        return true;  // take the card
    }
}
