package test.cards.cardtypes;

import main.game.TreasureCard;
import main.game.Turn;

public class Gold extends TreasureCard {
  public String getName() {
    return "Gold";
  }

  public int getCost() {
    return 6;
  }

  public void play(Turn turn) {
    turn.addMoney(3);
  }
}
