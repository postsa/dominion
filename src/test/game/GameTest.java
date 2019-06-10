package test.game;

import main.game.*;
import main.game.controller.Controller;
import main.game.controller.PlayerInput;
import main.game.exceptions.*;
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
        Cards purchasedCards = game.buyCard("Costs One");
        assertEquals(1, purchasedCards.count());
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
        Cards purchasedCards = game.buyCard("Costs One");
        assertEquals(1, purchasedCards.count());
    }

    @Test
    public void purchasedCardIsInstanceOfTestCard() {
        game.setPhase(BUY);
        game.playTreasure(new Copper());
        Cards purchasedCards = game.buyCard("Costs One");
        assertEquals(CostsOne.class, purchasedCards.getActionCards().get(0).getClass());
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
    public void buyingTwoEstatesResultInTwoOwnedEstate() {
        game.setPhase(BUY);
        game.addBuy();
        Cards purchasedCards = game.buyCard("Costs Zero");
        purchasedCards.put(game.buyCard("Costs Zero"));
        assertNotNull(purchasedCards.getActionCards());
        assertEquals(2, purchasedCards.getActionCards().size());
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
        assertFalse(game.isOver());
    }

    @Test
    public void quitPlayerInputSetsGameOverTrue() {
        PlayerInput input = new PlayerInput();
        input.setInputAction(QUIT);
        game.processPlayerInput(input);
        assertTrue(game.isOver());
    }
}
