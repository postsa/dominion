package main.game.exceptions;

public class NotEnoughMoneyToBuyCard extends RuntimeException {
  public NotEnoughMoneyToBuyCard(String message) {
    super(message);
  }
}
