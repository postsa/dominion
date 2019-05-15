package test.cards.cardtypes;

import main.game.Game;
import main.game.TreasureCard;

public class Copper extends TreasureCard {
    public String getName() {
        return "Copper";
    }

    public int getCost() {
        return 0;
    }

    public void play(Game game) {
        game.addMoney(1);
    }
}
