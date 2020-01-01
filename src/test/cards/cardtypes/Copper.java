package test.cards.cardtypes;

import main.game.TreasureCard;
import main.game.Turn;

public class Copper extends TreasureCard {
  public String getName() {
    return "Copper";
  }

  public int getCost() {
    return 0;
  }

  public void play(Turn turn) {
    turn.addMoney(1);
  }
}
