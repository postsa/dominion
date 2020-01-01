package main.game.commands;

import main.game.Command;
import main.game.TreasureCard;
import main.game.Turn;
import main.game.exceptions.GameNotInBuyPhase;

import static main.game.Phase.BUY;

public class PlayTreasure implements Command {

  private TreasureCard treasure;

  public PlayTreasure(TreasureCard treasure) {
    this.treasure = treasure;
  }

  public void execute(Turn turn) {
    if (turn.getPhase() != BUY)
      throw new GameNotInBuyPhase(
          String.format(
              "Cannot play treasure '%s' because the game is not in the buy phase.",
              treasure.getName()));
    treasure.play(turn);
  }
}
