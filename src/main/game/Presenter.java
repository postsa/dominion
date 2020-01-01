package main.game;

public interface Presenter {
  void displayCardsForSale(CardMerchant inventory);

  void displayHand(Hand hand);

  void displayAvailableMoney(int availableMoney);

  void displayPhase(Phase phase);

  void displayBuysRemaining(int buysRemaining);

  void displayActionsRemaining(int actionsRemaining);

  void displayInputError(Exception ex);
}
