package main.game.exceptions;

public class NoActionsRemaining extends RuntimeException {
  public NoActionsRemaining(String message) {
    super(message);
  }
}
