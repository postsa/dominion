package main.game.commands;

import main.game.Command;
import main.game.Turn;

import static main.game.Phase.OVER;

public class EndTurn implements Command {
  public void execute(Turn turn) {
    turn.setPhase(OVER);
  }
}
