package main.game.exceptions;

public class NoBuysRemaining extends RuntimeException {
  public NoBuysRemaining(String message) {
    super(message);
  }
}
