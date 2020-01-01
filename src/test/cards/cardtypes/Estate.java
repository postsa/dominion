package test.cards.cardtypes;

import main.game.VictoryCard;

public class Estate extends VictoryCard {
  public int getCost() {
    return 2;
  }

  public String getName() {
    return "Estate";
  }

  public int getVictoryPoints() {
    return 1;
  }
}
