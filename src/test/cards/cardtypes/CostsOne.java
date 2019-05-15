package test.cards.cardtypes;

import main.game.ActionCard;
import main.game.Game;

public class CostsOne extends ActionCard {
    public int getCost() {
        return 1;
    }

    public String getName() {
        return "Costs One";
    }

    public void act(Game game) {
    }
}
