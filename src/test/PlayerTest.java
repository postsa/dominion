package test;

import main.game.DeckOfCards;
import main.game.Player;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.CostsOne;
import test.cards.cardtypes.CostsZero;
import test.cards.cardtypes.Silver;
import test.game.mocks.MockHand;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

  private Player player;

  @Before
  public void setup() {
    this.player = new Player(new MockHand(), new DeckOfCards());
  }

  @Test
  public void drawingCardAddsItToHand() {
    player.getDeck().add(new CostsZero());
    player.drawCard();
    assertEquals(1, player.getHand().getInventory().count());
  }

  @Test
  public void discardingHandResultsInEmptyHand() {
    player.getDeck().add(new CostsZero());
    player.drawCard();
    assertEquals(1, player.getHand().getInventory().count());
    player.discardHand();
    assertEquals(0, player.getHand().getInventory().count());
  }

  @Test
  public void discardingHandGoesIntoDiscard() {
    player.getDeck().add(new CostsZero());
    player.drawCard();
    player.discardHand();
    assertEquals(1, player.getDiscard().count());
  }

  @Test
  public void drawingOneCardFromOneCardDeckResultsInEmptyDeck() {
    player.getDeck().add(new CostsZero());
    player.drawCard();
    assertEquals(0, player.getDeck().count());
  }

  @Test
  public void drawingACardAfterDrawingHandResultsInSixCardHand() {
    for (int i = 0; i < 6; i++) player.getDeck().add(new CostsZero());
    player.drawHand();
    player.drawCard();
    assertEquals(6, player.getHand().getInventory().count());
  }

  @Test
  public void ifDrawingMoreCardsThanAreInDeckAddDiscardPileToDeck() {
    for (int i = 0; i < 5; i++) player.getDiscard().add(new Silver());
    player.getDeck().add(new CostsZero());
    player.drawHand();
    assertEquals(5, player.getHand().getInventory().count());
    assertEquals(1, player.getDeck().count());
    assertEquals(0, player.getDiscard().count());
  }

  @Test
  public void drawingHandFromZeroCardDeckResultsInZeroCardHand() {
    player.drawHand();
    assertEquals(0, player.getHand().getInventory().count());
  }

  @Test
  public void draws5CardHandFrom5CardDeck() {
    for (int i = 0; i < 5; i++) player.getDeck().add(new CostsOne());
    player.drawHand();
    assertEquals(5, player.getHand().getInventory().count());
  }
}
