package test.cards.cardtypes;

import main.game.ActionCard;
import main.game.Game;

public class AddsOneBuy extends ActionCard {
    public int getCost() {
        return 5;
    }

    public String getName() {
        return "Add One Buy";
    }

    public void act(Game game) {
        game.addBuy();
    }
}
