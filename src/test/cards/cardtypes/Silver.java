package test.cards.cardtypes;

import main.game.Game;
import main.game.TreasureCard;

public class Silver extends TreasureCard {
    public String getName() {
        return "Silver";
    }

    public int getCost() {
        return 3;
    }

    public void play(Game game) {
        game.addMoney(2);
    }
}
