package test.game.mocks;

import main.cards.PlayerHand;
import main.game.*;
import main.game.exceptions.NoSuchCardAvailable;
import test.cards.cardtypes.CostsOne;
import test.cards.cardtypes.CostsZero;
import test.cards.mocks.MockCardRegistry;

import java.util.Set;

public class MockCardMerchant extends MockInventory implements CardMerchant {
  public Cards requestCard(String name) {
    Cards cards = new Cards();
    switch (name) {
      case "Costs Zero":
        cards.add(new CostsZero());
        break;
      case "Costs One":
        cards.add(new CostsOne());
        break;
    }
    return cards;
  }

  public Purchasable requestCardInfo(String cardName) {
    switch (cardName) {
      case "Costs Zero":
        return new MockPurchasable().withCost(0).withName(cardName);
      case "Costs One":
        return new MockPurchasable().withCost(1).withName(cardName);
      default:
        throw new NoSuchCardAvailable("TILT");
    }
  }

  public void registerCard(ActionCard card) {}

  public void registerCard(VictoryCard card) {}

  public void registerCard(TreasureCard card) {}

  public Cards getInventory() {
    return null;
  }

  public int countCardsWithName(String cardName) {
    return 0;
  }

  public Set<String> requestAvailableCardNames() {
    return null;
  }

  public DeckOfCards getStartingDeck() {
    return new DeckOfCards();
  }

  public Hand createHand() {
    return new PlayerHand(new MockCardRegistry());
  }
}
