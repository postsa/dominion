package test.game;

import main.game.*;

public class ConsolePresentor implements Presentor {
    public void displayAvailableCards(Cards cards) {
        displayActionCards(cards);
        System.out.print("\n");
        displayTreasureCards(cards);
        System.out.print("\n");
        displayVictoryCards(cards);
    }

    private void displayActionCards(Cards cards) {
        System.out.println("Action Cards");
        for (ActionCard card : cards.getActionCards()) {
            System.out.println(card.getName());
        }
    }

    private void displayTreasureCards(Cards cards) {
        System.out.println("Treasure Cards");
        for (TreasureCard card : cards.getTreasure()) {
            System.out.println(card.getName());
        }
    }

    private void displayVictoryCards(Cards cards) {
        System.out.println("Victory Cards");
        for (VictoryCard card : cards.getVictoryCards()) {
            System.out.println(card.getName());
        }
    }
}
