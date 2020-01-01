package main.game.exceptions;

public class GameNotInActionPhase extends RuntimeException {
  public GameNotInActionPhase(String message) {
    super(message);
  }
}
