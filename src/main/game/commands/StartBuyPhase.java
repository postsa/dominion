package main.game.commands;

import main.game.Command;
import main.game.Turn;

import static main.game.Phase.BUY;

public class StartBuyPhase implements Command {
  public void execute(Turn turn) {
    turn.setPhase(BUY);
    turn.initializeBuysRemaining();
  }
}
