package main.game.exceptions;

public class GameNotInBuyPhase extends RuntimeException {
  public GameNotInBuyPhase(String message) {
    super(message);
  }
}
