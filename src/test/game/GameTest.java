package test.game;

import main.game.Cards;
import main.game.controller.Controller;
import main.game.Presenter;
import main.game.controller.PlayerInput;
import main.game.exceptions.*;
import main.game.CardMerchant;
import main.game.Game;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.*;
import test.game.mocks.MockCardMerchant;
import test.game.mocks.MockController;
import test.game.mocks.MockPresenter;

import static main.game.Phase.*;
import static main.game.controller.InputAction.*;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setup() {
        CardMerchant cardMerchant = new MockCardMerchant();
        Presenter presenter = new MockPresenter();
        Controller controller = new MockController();
        this.game = new Game(cardMerchant, presenter, controller);
    }

    @Test(expected = NotEnoughMoneyToBuyCard.class)
    public void cannotBuyCardIfNotEnoughMoney() {
        game.setPhase(BUY);
        game.buyCard("Costs One");
    }

    @Test(expected = NoSuchCardAvaliable.class)
    public void canOnlyBuyCardIfAvailable() {
        game.setPhase(BUY);
        game.buyCard("Not Available");
    }

    @Test
    public void takingCardActionSubtractsOneAction() {
        game.takeAction(new CostsOne());
        assertEquals(0, game.getActionsRemaining());
    }

    @Test
    public void canBuyCard() {
        game.setPhase(BUY);
        game.playTreasure(new Copper());
        game.buyCard("Costs One");
        assertEquals(1, game.getHand().getInventory().count());
    }

    @Test
    public void buyingCardSubtractsCostOfCard() {
        game.setPhase(BUY);
        game.playTreasure(new Copper());
        game.buyCard("Costs One");
        assertEquals(0, game.getMoney());
    }

    @Test
    public void canBuyIfBuysRemaining() {
        game.setPhase(BUY);
        assertEquals(1, game.getBuysRemaining());
        game.playTreasure(new Copper());
        game.buyCard("Costs One");
        assertEquals(1, game.getHand().getInventory().count());
    }

    @Test
    public void purchasedCardIsInstanceOfTestCard() {
        game.setPhase(BUY);
        game.playTreasure(new Copper());
        game.buyCard("Costs One");
        assertEquals(CostsOne.class, game.getHand().getInventory().getActionCards().get(0).getClass());
    }

    @Test
    public void addOneBuyCardAddsOneBuy() {
        game.takeAction(new AddsOneBuy());
        assertEquals(2, game.getBuysRemaining());
    }

    @Test
    public void addOneActionCardAddsOneAction() {
        game.takeAction(new AddsOneAction());
        assertEquals(1, game.getActionsRemaining());
    }

    @Test
    public void gameStartsInActionPhase() {
        assertEquals(ACTION, game.getPhase());
    }

    @Test(expected = GameNotInBuyPhase.class)
    public void canOnlyPlayTreasureInBuyPhase() {
        game.setPhase(ACTION);
        game.playTreasure(new Copper());
    }

    @Test(expected = GameNotInBuyPhase.class)
    public void canOnlyBuyCardInBuyPhase() {
        game.setPhase(ACTION);
        game.buyCard("Costs One");
    }

    @Test(expected = GameNotInActionPhase.class)
    public void canOnlyTakeActionInActionPhase() {
        game.setPhase(BUY);
        game.takeAction(new AddsOneAction());
    }

    @Test
    public void discardingHandResultsInEmptyHand() {
        game.setPhase(BUY);
        game.playTreasure(new Copper());
        game.buyCard("Costs One");
        game.setPhase(CLEAN_UP);
        game.discardHand();
        assertEquals(0, game.getHand().getInventory().count());
    }

    @Test
    public void discardingHandGoesIntoDiscard() {
        game.setPhase(BUY);
        game.playTreasure(new Copper());
        game.buyCard("Costs One");
        game.setPhase(CLEAN_UP);
        game.discardHand();
        assertEquals(1, game.getDiscard().count());
    }

    @Test
    public void drawingHandFromZeroCardDeckResultsInZeroCardHand() {
        game.setPhase(CLEAN_UP);
        game.drawHand();
        assertEquals(0, game.getHand().getInventory().count());
    }

    @Test
    public void draws5CardHandFrom5CardDeck() {
        for (int i = 0; i < 5; i++)
            game.getDeck().add(new CostsOne());
        game.setPhase(CLEAN_UP);
        game.drawHand();
        assertEquals(5, game.getHand().getInventory().count());
    }

    @Test(expected = GameNotInCleanUpPhase.class)
    public void cannotDiscardHandWhenNotInCleanupPhase() {
        game.discardHand();
    }

    @Test(expected = GameNotInCleanUpPhase.class)
    public void cannotDrawHandWhenNotInCleanupPhase() {
        game.drawHand();
    }

    @Test
    public void buyingTwoEstatesResultInTwoOwnedEstate() {
        game.setPhase(BUY);
        game.addBuy();
        game.buyCard("Costs Zero");
        game.buyCard("Costs Zero");
        assertNotNull(game.getHand().getInventory().getActionCards());
        assertEquals(2, game.getHand().getInventory().getActionCards().size());
    }

    @Test
    public void playingOneCopperYieldsOneMoney() {
        game.setPhase(BUY);
        game.playTreasure(new Copper());
        assertEquals(1, game.getMoney());
    }

    @Test
    public void playingOneSilverYieldsTwoMoney() {
        game.setPhase(BUY);
        game.playTreasure(new Silver());
        assertEquals(2, game.getMoney());
    }

    @Test
    public void playingOneGoldYieldsThreeMoney() {
        game.setPhase(BUY);
        game.playTreasure(new Gold());
        assertEquals(3, game.getMoney());
    }

    @Test
    public void drawingOneCardFromOneCardDeckResultsInEmptyDeck() {
        game.getDeck().add(new CostsZero());
        game.drawCard();
        assertEquals(0, game.getDeck().count());
    }

    @Test
    public void drawingCardAddsItToHand() {
        game.getDeck().add(new CostsZero());
        game.drawCard();
        assertEquals(1, game.getHand().getInventory().count());
    }

    @Test
    public void drawingACardAfterDrawingHandResultsInSixCardHand() {
        for(int i = 0; i < 6; i++)
            game.getDeck().add(new CostsZero());
        game.setPhase(CLEAN_UP);
        game.drawHand();
        game.drawCard();
        assertEquals(6, game.getHand().getInventory().count());
    }

    @Test
    public void ifDrawingMoreCardsThanAreInDeckAddDiscardPileToDeck() {
        for(int i = 0; i < 5; i++)
            game.getDiscard().add(new Silver());
        game.getDeck().add(new CostsZero());
        game.setPhase(CLEAN_UP);
        game.drawHand();
        assertEquals(5, game.getHand().getInventory().count());
        assertEquals(1, game.getDeck().count());
        assertEquals(0, game.getDiscard().count());
    }

    @Test
    public void playingCardsAddsTakesCardActions() {
        game.setPhase(BUY);
        game.playCards(new Cards().withTreasureCards(new Copper(), new Copper()));
        assertEquals(game.getMoney(), 2);
    }

    @Test
    public void advancePhaseCorrectlyAdvancesPhase() {
        game.advancePhase();
        assertEquals(BUY, game.getPhase());
        game.advancePhase();
        assertEquals(CLEAN_UP, game.getPhase());
        game.advancePhase();
        assertEquals(ACTION, game.getPhase());
    }

    @Test
    public void advancePhasePlayerActionAdvancesPhase() {
        PlayerInput input = new PlayerInput();
        input.setInputAction(ADVANCE_TURN);
        game.processPlayerInput(input);
        assertEquals(BUY, game.getPhase());
    }

    @Test
    public void gameOverInitializedAsFalse() {
        assertEquals(false, game.isOver());
    }

    @Test
    public void quitPlayerInputSetsGameOverTrue() {
        PlayerInput input = new PlayerInput();
        input.setInputAction(QUIT);
        game.processPlayerInput(input);
        assertEquals(true, game.isOver());
    }
}
