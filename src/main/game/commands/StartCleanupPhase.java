package main.game.commands;

import main.game.Command;
import main.game.Turn;

import static main.game.Phase.CLEAN_UP;

public class StartCleanupPhase implements Command {
  public void execute(Turn turn) {
    turn.setPhase(CLEAN_UP);
    turn.initializeMoney();
  }
}
