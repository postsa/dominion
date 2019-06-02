package test.game;

import main.cards.CardCatalog;
import main.cards.Supply;
import main.game.controller.Controller;
import main.game.Game;
import main.game.Presenter;
import test.cards.cardtypes.*;

public class TestMain {
    public static void main(String[] args) {
        Supply supply = setupTestSupply();
        Presenter presenter = new ConsolePresenter();
        Controller controller = new ConsoleController();
        Game game = new Game(supply, presenter, controller);
        while(!game.isOver()) {
            game.takePresentableTurn();
        }
    }

    private static Supply setupTestSupply() {
        CardCatalog cardCatalog = new CardCatalog();
        AddsOneAction addsOneAction = new AddsOneAction();
        AddsOneBuy addsOneBuy = new AddsOneBuy();
        Copper copper = new Copper();
        Silver silver = new Silver();
        Estate estate = new Estate();
        cardCatalog.registerCard(addsOneAction);
        cardCatalog.registerCard(addsOneBuy);
        cardCatalog.registerCard(copper);
        cardCatalog.registerCard(silver);
        cardCatalog.registerCard(estate);
        Supply supply = new Supply(cardCatalog);
        supply.addCardByName(addsOneAction.getName(), 1);
        supply.addCardByName(addsOneBuy.getName(), 2);
        supply.addCardByName(copper.getName(), 3);
        supply.addCardByName(silver.getName());
        supply.addCardByName(estate.getName(), 2);
        return supply;
    }
}
