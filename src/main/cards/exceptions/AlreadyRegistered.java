package main.cards.exceptions;

public class AlreadyRegistered extends RuntimeException {
  public AlreadyRegistered(String message) {
    super(message);
  }
}
