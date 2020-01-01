package main.game.exceptions;

public class CannotPlayVictoryCard extends RuntimeException {
  public CannotPlayVictoryCard(String message) {
    super(message);
  }
}
