package main.game.controller;

public class PlayerInput {
  private String cardName;
  private InputAction inputAction;

  public String getCardName() {
    return cardName;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public InputAction getInputAction() {
    return inputAction;
  }

  public void setInputAction(InputAction inputAction) {
    this.inputAction = inputAction;
  }
}
