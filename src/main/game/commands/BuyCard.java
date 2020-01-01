package main.game.commands;

import main.game.Command;
import main.game.Purchasable;
import main.game.Turn;
import main.game.exceptions.GameNotInBuyPhase;
import main.game.exceptions.NoBuysRemaining;
import main.game.exceptions.NotEnoughMoneyToBuyCard;

import static main.game.Phase.BUY;

public class BuyCard implements Command {

  private Purchasable cardInfo;

  public BuyCard(Purchasable cardInfo) {
    this.cardInfo = cardInfo;
  }

  public void execute(Turn turn) {
    if (turn.getPhase() != BUY)
      throw new GameNotInBuyPhase("Cannot buy card because the game is not in the buy phase.");
    if (turn.notEnoughMoneyToBuy(this.cardInfo))
      throw new NotEnoughMoneyToBuyCard("Cannot buy '%s', insufficient money.");
    if (turn.noBuysRemaining())
      throw new NoBuysRemaining("Cannot buy '%s' because no buys are remaining.");
    turn.subtractMoney(cardInfo.getCost());
    turn.subtractBuy();
  }
}
