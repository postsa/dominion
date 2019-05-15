package test.cards.cardtypes;

import main.game.Game;
import main.game.TreasureCard;

public class Gold extends TreasureCard {
    public String getName() {
        return "Gold";
    }

    public int getCost() {
        return 6;
    }

    public void play(Game game) {
        game.addMoney(3);
    }
}
