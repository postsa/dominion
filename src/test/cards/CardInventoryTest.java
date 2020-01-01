package test.cards;

import main.cards.CardInventory;
import main.game.CardRegistry;
import main.game.Cards;
import main.game.exceptions.NoSuchCardAvailable;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.CostsOne;
import test.cards.cardtypes.CostsZero;
import test.cards.mocks.MockCardRegistry;

import static junit.framework.TestCase.assertEquals;

public class CardInventoryTest {

  private CardInventory cardInventory;
  private CostsOne costsOne;
  private CardRegistry cardRegistry;

  @Before
  public void setup() {
    costsOne = new CostsOne();
    cardRegistry = new MockCardRegistry();
    this.cardInventory = new CardInventory(cardRegistry);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cannotAddNegativeQuantityToInventory() {
    cardInventory.addCardByName(costsOne.getName(), -1);
  }

  @Test(expected = NoSuchCardAvailable.class)
  public void cannotAddInventoryIfCardNotRegistered() {
    cardInventory.addCardByName("Doesn't Exist");
  }

  @Test(expected = NoSuchCardAvailable.class)
  public void cannotRequestCardWithNoInventory() {
    cardInventory.requestCard(costsOne.getName());
  }

  @Test
  public void addingCardToInventoryResultsInAdditionalInventory() {
    cardInventory.addCardByName(costsOne.getName());
    assertEquals(1, cardInventory.countCardsWithName(costsOne.getName()));
  }

  @Test
  public void addingCardToInventoryWithQuantityIsReflectedInInventory() {
    cardInventory.addCardByName(costsOne.getName(), 2);
    assertEquals(2, cardInventory.countCardsWithName(costsOne.getName()));
  }

  @Test
  public void cardInventoryIsSpecificToTheCardName() {
    CostsZero costsZero = new CostsZero();
    cardInventory.addCardByName(costsOne.getName(), 2);
    cardRegistry.registerCard(costsZero);
    cardInventory.addCardByName(costsZero.getName());
    assertEquals(2, cardInventory.countCardsWithName(costsOne.getName()));
  }

  @Test
  public void canRequestRegisteredCardFromInventoryIfInStock() {
    cardInventory.addCardByName(costsOne.getName());
    Cards cards = cardInventory.requestCard(costsOne.getName());
    assertEquals(1, cards.count());
    assertEquals(costsOne.getName(), cards.getActionCards().get(0).getName());
  }
}
