package test.game.mocks;

import main.game.Cards;
import main.game.Hand;

import java.util.Set;

public class MockHand implements Hand {

    private Cards cards;

    public MockHand() {
        this.cards = new Cards();
    }

    public Cards takeCardsFromHandByName(String... cardName) {
        return null;
    }

    public Cards takeCardsFromHandByName(String cardName, int quantity) {
        return null;
    }

    public void put(Cards cards) {
        this.cards.put(cards);
    }

    public Cards requestCard(String name) {
        return null;
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
        return this.cards;
    }

    public Set<String> requestAvailableCardNames() {
        return null;
    }
}
