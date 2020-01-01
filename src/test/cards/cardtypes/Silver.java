package test.cards.cardtypes;

import main.game.TreasureCard;
import main.game.Turn;

public class Silver extends TreasureCard {
  public String getName() {
    return "Silver";
  }

  public int getCost() {
    return 3;
  }

  public void play(Turn turn) {
    turn.addMoney(2);
  }
}
