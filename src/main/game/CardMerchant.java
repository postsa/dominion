package main.game;

import java.util.Set;

public interface CardMerchant extends Inventory {
    Cards requestCard(String name);

    Purchasable requestCardInfo(String cardName);

    Cards getInventory();

    int countCardsWithName(String cardName);

    Set<String> requestAvailableCardNames();

    DeckOfCards getStartingDeck();

    Hand createHand();
}
