package test.cards.cardtypes;

import main.game.ActionCard;
import main.game.Game;

public class CostsZero extends ActionCard {
    public void act(Game game) {

    }

    public String getName() {
        return "Costs Zero";
    }

    public int getCost() {
        return 0;
    }
}
