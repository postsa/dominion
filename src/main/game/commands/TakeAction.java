package main.game.commands;

import main.game.ActionCard;
import main.game.Command;
import main.game.Phase;
import main.game.Turn;
import main.game.exceptions.GameNotInActionPhase;
import main.game.exceptions.NoActionsRemaining;

public class TakeAction implements Command {
  private ActionCard action;

  public TakeAction(ActionCard action) {
    this.action = action;
  }

  public void execute(Turn turn) {
    if (turn.getPhase() != Phase.ACTION)
      throw new GameNotInActionPhase(
          String.format(
              "Cannot play '%s' because the game is not in the action phase", action.getName()));
    if (turn.noActionsRemaining())
      throw new NoActionsRemaining(
          String.format("No actions available to play '%s'.", this.action.getName()));
    this.action.act(turn);
    turn.subtractAction();
  }
}
