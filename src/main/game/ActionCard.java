package main.game;

public abstract class ActionCard implements Purchasable {
  public abstract void act(Turn turn);
}
