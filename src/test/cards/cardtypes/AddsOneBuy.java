package test.cards.cardtypes;

import main.game.ActionCard;
import main.game.Turn;

public class AddsOneBuy extends ActionCard {
  public int getCost() {
    return 5;
  }

  public String getName() {
    return "Adds One Buy";
  }

  public void act(Turn turn) {
    turn.addBuy();
  }
}
