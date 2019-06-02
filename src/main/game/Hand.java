package main.game;

public interface Hand extends Inventory {
    Cards takeCardsFromHandByName(String... cardName);

    Cards takeCardsFromHandByName(String cardName, int quantity);

    void put(Cards cards);
}
