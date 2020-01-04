package test.game;

import main.game.*;

public class ConsolePresenter implements Presenter {

  public void displayCardsForSale(CardMerchant cardMerchant) {
    System.out.println("---- Supply ----");
    for (String name : cardMerchant.requestAvailableCardNames()) {
      System.out.println(
          String.format(
              "%s x%s (%s)",
              name,
              cardMerchant.countCardsWithName(name),
              cardMerchant.requestCardInfo(name).getCost()));
    }
    System.out.print("\n");
  }

  public void displayHand(Hand hand) {
    System.out.println("---- Hand ----");
    for (String name : hand.requestAvailableCardNames()) {
      if (hand.countCardsWithName(name) > 0)
        System.out.println(String.format("%s x%s", name, hand.countCardsWithName(name)));
    }
    System.out.print("\n");
  }

  public void displayAvailableMoney(int availableMoney) {
    System.out.println(String.format("Available Money: %s.", availableMoney));
  }

  public void displayPhase(Phase phase) {
    System.out.println(String.format("Current Phase: %s.", phase));
  }

  public void displayBuysRemaining(int buysRemaining) {
    System.out.println(String.format("Buys Remaining: %s.", buysRemaining));
  }

  public void displayActionsRemaining(int actionsRemaining) {
    System.out.println(String.format("Actions Remaining: %s.", actionsRemaining));
  }

  public void displayInputError(Exception ex) {
    System.out.println(ex.getMessage());
  }

  public void displayPlayerName(String name) {
    System.out.println(String.format("Current Player: %s.", name));
  }

  private void displayActionCards(Cards cards) {
    System.out.println("Action Cards");
    for (ActionCard card : cards.getActionCards()) {
      System.out.println(card.getName());
    }
  }

  private void displayTreasureCards(Cards cards) {
    System.out.println("Treasure Cards");
    for (TreasureCard card : cards.getTreasure()) {
      System.out.println(card.getName());
    }
  }

  private void displayVictoryCards(Cards cards) {
    System.out.println("Victory Cards");
    for (VictoryCard card : cards.getVictoryCards()) {
      System.out.println(card.getName());
    }
  }
}
