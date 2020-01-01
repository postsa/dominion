package main.game;

public interface Hand extends Inventory {
  Cards takeCards(String... cardName);

  Cards takeCards(String cardName, int quantity);

  void put(Cards cards);
}
