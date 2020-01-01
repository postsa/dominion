package main.game;

import java.util.ArrayList;
import java.util.List;

public class Turn {
  int money;
  int actionsRemaining;
  int buysRemaining;
  private Phase phase;
  private List<Command> commands;

  public Turn() {
    this.commands = new ArrayList<>();
  }

  public void accept(Command command) {
    command.execute(this);
    commands.add(command);
  }

  public Phase getPhase() {
    return this.phase;
  }

  public void setPhase(Phase phase) {
    this.phase = phase;
  }

  public int getMoney() {
    return money;
  }

  public int getActionsRemaining() {
    return this.actionsRemaining;
  }

  public int getBuysRemaining() {
    return this.buysRemaining;
  }

  public void addBuy() {
    this.buysRemaining++;
  }

  public void subtractBuy() {
    this.buysRemaining--;
  }

  public void addMoney(int amount) {
    this.money += amount;
  }

  public void subtractMoney(int amount) {
    this.money -= amount;
  }

  public void addAction() {
    this.actionsRemaining++;
  }

  public boolean noActionsRemaining() {
    return getActionsRemaining() <= 0;
  }

  public void subtractAction() {
    this.actionsRemaining--;
  }

  public void initializeActionsRemaining() {
    this.actionsRemaining = 1;
  }

  public void initializeMoney() {
    this.money = 0;
  }

  public void initializeBuysRemaining() {
    this.buysRemaining = 1;
  }

  public boolean notEnoughMoneyToBuy(Purchasable cardInfo) {
    return this.getMoney() < cardInfo.getCost();
  }

  public boolean noBuysRemaining() {
    return this.getBuysRemaining() <= 0;
  }
}
