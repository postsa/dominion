package main.cards;

import main.game.CardRegistry;
import main.game.Cards;
import main.game.Hand;

public class PlayerHand extends CardInventory implements Hand {

  public PlayerHand(CardRegistry cardRegistry) {
    super(cardRegistry);
  }

  public Cards takeCards(String... cardNames) {
    Cards takenCards = new Cards();
    for (String name : cardNames) takenCards.put(this.takeCardByName(name));
    return takenCards;
  }

  public Cards takeCards(String cardName, int quantity) {
    Cards takenCards = new Cards();
    for (int i = 0; i < quantity; i++) takenCards.put(this.takeCardByName(cardName));
    return takenCards;
  }

  public void put(Cards cards) {
    this.inventory.put(cards);
  }
}
