package main.game;

import java.util.Set;

public interface Inventory {
  Cards requestCard(String name);

  Cards takeCardByName(String name);

  int countCardsWithName(String cardName);

  void addCardByName(String cardName);

  void addCardByName(String cardName, int quantity);

  Cards getInventory();

  Set<String> requestAvailableCardNames();
}
