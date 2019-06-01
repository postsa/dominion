package test.game;

import main.cards.Supply;
import main.game.Game;
import main.game.Presentor;
import test.cards.cardtypes.*;

public class TestMain {
    public static void main(String[] args) {
        Supply supply = setupTestSupply();
        Presentor presentor = new ConsolePresentor();
        Game game = new Game(supply, presentor);
        game.listAvailableCards();
    }

    private static Supply setupTestSupply() {
        Supply supply = new Supply();
        AddsOneAction addsOneAction = new AddsOneAction();
        supply.registerCard(addsOneAction);
        AddsOneBuy addsOneBuy = new AddsOneBuy();
        supply.registerCard(addsOneBuy);
        Copper copper = new Copper();
        supply.registerCard(copper);
        Silver silver = new Silver();
        supply.registerCard(silver);
        Estate estate = new Estate();
        supply.registerCard(estate);
        supply.addInventory(addsOneAction.getName(), 1);
        supply.addInventory(addsOneBuy.getName(), 2);
        supply.addInventory(copper.getName(), 3);
        supply.addInventory(silver.getName());
        supply.addInventory(estate.getName(), 2);
        return supply;
    }
}
