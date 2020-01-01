package main.game.commands;

import main.game.Command;
import main.game.Turn;

import static main.game.Phase.ACTION;

public class StartActionPhase implements Command {
  public void execute(Turn turn) {
    turn.setPhase(ACTION);
    turn.initializeActionsRemaining();
  }
}
