package test.cards.cardtypes;

import main.game.ActionCard;
import main.game.Game;

public class AddsOneAction extends ActionCard {
    public int getCost() {
        return 3;
    }

    public String getName() {
        return "Adds One Action";
    }

    public void act(Game game) {
        game.addAction();
    }
}
