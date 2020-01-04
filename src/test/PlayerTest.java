package test;

import main.game.Cards;
import main.game.Player;
import main.game.Turn;
import main.game.exceptions.*;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.*;
import test.game.mocks.MockCardMerchant;

import static main.game.Phase.*;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

  private Player player;

  @Before
  public void setup() {
    this.player = new Player("Test Bruh", new MockCardMerchant());
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

  @Test(expected = GameNotInBuyPhase.class)
  public void canOnlyPlayTreasureInBuyPhase() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(ACTION);
    player.playTreasure(new Copper());
  }

  @Test(expected = NotEnoughMoneyToBuyCard.class)
  public void cannotBuyCardIfNotEnoughMoney() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.buyCard("Costs One");
  }

  @Test(expected = NoSuchCardAvailable.class)
  public void canOnlyBuyCardIfAvailable() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.buyCard("Not Available");
  }

  @Test(expected = GameNotInBuyPhase.class)
  public void canOnlyBuyCardInBuyPhase() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(ACTION);
    player.buyCard("Costs One");
  }

  @Test
  public void buyingTwoEstatesResultInTwoOwnedEstate() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    turn.addBuy();
    player.buyCard("Costs Zero");
    player.buyCard("Costs Zero");
    assertEquals(2, player.getDiscard().count("Costs Zero"));
  }

  @Test
  public void canBuyCard() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.playTreasure(new Copper());
    player.buyCard("Costs One");
    assertEquals(1, player.getDiscard().count("Costs One"));
  }

  @Test
  public void buyingCardSubtractsCostOfCard() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.playTreasure(new Copper());
    player.buyCard("Costs One");
    assertEquals(0, turn.getMoney());
  }

  @Test
  public void canBuyIfBuysRemaining() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    assertEquals(1, turn.getBuysRemaining());
    player.playTreasure(new Copper());
    player.buyCard("Costs One");
    assertEquals(1, player.getDiscard().count("Costs One"));
  }

  @Test
  public void purchasedCardIsInstanceOfTestCard() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.playTreasure(new Copper());
    player.buyCard("Costs One");
    assertEquals(CostsOne.class, player.getDiscard().getActionCards().get(0).getClass());
  }

  @Test
  public void playingOneCopperYieldsOneMoney() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.playTreasure(new Copper());
    assertEquals(1, turn.getMoney());
  }

  @Test
  public void playingOneSilverYieldsTwoMoney() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.playTreasure(new Silver());
    assertEquals(2, turn.getMoney());
  }

  @Test
  public void playingOneGoldYieldsThreeMoney() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.playTreasure(new Gold());
    assertEquals(3, turn.getMoney());
  }

  @Test(expected = GameNotInActionPhase.class)
  public void canOnlyTakeActionInActionPhase() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.takeAction(new AddsOneAction());
  }

  @Test
  public void takingCardActionSubtractsOneAction() {
    Turn turn = new Turn();
    player.startTurn(turn);
    player.takeAction(new CostsOne());
    assertEquals(0, turn.getActionsRemaining());
  }

  @Test
  public void addOneBuyCardAddsOneBuy() {
    Turn turn = new Turn();
    player.startTurn(turn);
    player.takeAction(new AddsOneBuy());
    assertEquals(2, turn.getBuysRemaining());
  }

  @Test
  public void addOneActionCardAddsOneAction() {
    Turn turn = new Turn();
    player.startTurn(turn);
    player.takeAction(new AddsOneAction());
    assertEquals(1, turn.getActionsRemaining());
  }

  @Test
  public void playingCardsAddsMoneyTakesCardActions() {
    Turn turn = new Turn();
    player.startTurn(turn);
    turn.setPhase(BUY);
    player.playCards(new Cards().withTreasureCards(new Copper(), new Copper()));
    assertEquals(turn.getMoney(), 2);
  }

  @Test(expected = CannotPlayVictoryCard.class)
  public void cannotPlayVictoryCard() {
    Turn turn = new Turn();
    player.startTurn(turn);
    player.playCards(new Cards().withVictoryCards(new Estate()));
  }

  @Test
  public void advancePhaseCorrectlyAdvancesPhase() {
    Turn turn = new Turn();
    player.startTurn(turn);
    player.advancePhase();
    assertEquals(BUY, turn.getPhase());
    player.advancePhase();
    assertEquals(CLEAN_UP, turn.getPhase());
    player.advancePhase();
    assertEquals(OVER, turn.getPhase());
  }
}
