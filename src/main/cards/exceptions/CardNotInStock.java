package main.cards.exceptions;

public class CardNotInStock extends RuntimeException {
  public CardNotInStock(String message) {
    super(message);
  }
}
