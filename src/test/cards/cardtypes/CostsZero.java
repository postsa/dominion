package test.cards.cardtypes;

import main.game.ActionCard;
import main.game.Turn;

public class CostsZero extends ActionCard {
  public String getName() {
    return "Costs Zero";
  }

  public int getCost() {
    return 0;
  }

  public void act(Turn turn) {}
}
