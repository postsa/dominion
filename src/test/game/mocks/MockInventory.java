package test.game.mocks;

import main.game.Cards;
import main.game.Inventory;

import java.util.Set;

public class MockInventory implements Inventory {
    public Cards requestCard(String name) {
        return null;
    }

    public void addCardsByName(String name) {

    }

    public Cards takeCardsByName(String name) {
        return null;
    }

    public int countCardsWithName(String cardName) {
        return 0;
    }

    public void addCardByName(String cardName) {

    }

    public void addCardByName(String cardName, int quantity) {

    }

    public Cards getInventory() {
        return null;
    }

    public Set<String> requestAvailableCardNames() {
        return null;
    }
}
