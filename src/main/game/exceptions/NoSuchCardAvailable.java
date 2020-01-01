package main.game.exceptions;

public class NoSuchCardAvailable extends RuntimeException {
  public NoSuchCardAvailable(String message) {
    super(message);
  }
}
