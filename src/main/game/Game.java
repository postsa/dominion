package main.game;

import main.game.commands.*;
import main.game.controller.Controller;
import main.game.controller.PlayerInput;
import main.game.exceptions.CannotPlayVictoryCard;

import static main.game.Phase.ACTION;

public class Game {
  private final Turn turn = new Turn();
  private Player player;
  private boolean isOver;
  private CardMerchant cardMerchant;
  private Presenter presenter;
  private Controller controller;

  public Game(CardMerchant cardMerchant, Presenter presenter, Controller controller) {
    this.turn.actionsRemaining = 1;
    this.turn.buysRemaining = 1;
    this.turn.money = 0;
    this.cardMerchant = cardMerchant;
    this.player = new Player(cardMerchant.createHand(), cardMerchant.getStartingDeck().shuffle());
    this.player.drawHand();
    this.turn.setPhase(ACTION);
    this.presenter = presenter;
    this.controller = controller;
  }

  public boolean isOver() {
    return isOver;
  }

  private void setOver(boolean over) {
    isOver = over;
  }

  public Phase getPhase() {
    return turn.getPhase();
  }

  public void setPhase(Phase phase) {
    turn.setPhase(phase);
  }

  public int getMoney() {
    return turn.getMoney();
  }

  public Cards buyCard(String cardName) {
    Cards purchasedCards = new Cards();
    Purchasable cardInfo = cardMerchant.requestCardInfo(cardName);

    Command buyCard = new BuyCard(cardInfo);
    this.turn.accept(buyCard);

    // This actually takes the card out of the deck
    Cards cardToBuy = cardMerchant.requestCard(cardInfo.getName());
    purchasedCards.put(cardToBuy);
    return purchasedCards;
  }

  public void playTreasure(TreasureCard treasure) {
    Command playTreasure = new PlayTreasure(treasure);
    this.turn.accept(playTreasure);
  }

  public int getActionsRemaining() {
    return turn.getActionsRemaining();
  }

  public int getBuysRemaining() {
    return turn.getBuysRemaining();
  }

  public void addBuy() {
    turn.addBuy();
  }

  public void takeAction(ActionCard actionCard) {
    Command takeAction = new TakeAction(actionCard);
    this.turn.accept(takeAction);
  }

  private void displaySupply() {
    presenter.displayCardsForSale(this.cardMerchant);
  }

  private void displayHand() {
    presenter.displayHand(player.getHand());
  }

  private void displayAvailableMoney() {
    presenter.displayAvailableMoney(this.turn.getMoney());
  }

  private void displayPhase() {
    presenter.displayPhase(turn.getPhase());
  }

  private void displayBuysRemaining() {
    presenter.displayBuysRemaining(turn.getBuysRemaining());
  }

  private void displayActionsRemaining() {
    presenter.displayActionsRemaining(turn.getActionsRemaining());
  }

  private void displayTurnInformation() {
    this.displaySupply();
    this.displayHand();
    this.displayPhase();
    this.displayAvailableMoney();
    this.displayBuysRemaining();
    this.displayActionsRemaining();
  }

  public void takePresentableTurn() {
    this.displayTurnInformation();
    this.takeTurn();
  }

  private void takeTurn() {
    try {
      PlayerInput input = this.controller.acceptInput();
      this.processPlayerInput(input);
    } catch (Exception ex) {
      presenter.displayInputError(ex);
    }
  }

  public void processPlayerInput(PlayerInput input) {
    switch (input.getInputAction()) {
      case QUIT:
        this.setOver(true);
        break;
      case ADVANCE_TURN:
        this.advancePhase();
        break;
      case BUY_CARD:
        player.addCardsToDiscard(this.buyCard(input.getCardName()));
        break;
      case PLAY_CARD:
        Cards cardsToPlay = this.player.takeCardsFromHand(input.getCardName());
        this.playCards(cardsToPlay);
        break;
    }
  }

  public void advancePhase() {
    switch (this.turn.getPhase()) {
      case ACTION:
        this.turn.accept(new StartBuyPhase());
        break;
      case BUY:
        this.player.discardHand();
        this.player.drawHand();
        this.turn.accept(new StartCleanupPhase());
        break;
      case CLEAN_UP:
        this.turn.accept(new StartActionPhase());
        break;
    }
  }

  public void playCards(Cards cards) {
    if (!cards.getVictoryCards().empty()) {
      throw new CannotPlayVictoryCard("Victory cards cannot be played.");
    }
    for (TreasureCard treasureCard : cards.getTreasure()) {
      playTreasure(treasureCard);
    }
    for (ActionCard actionCard : cards.getActionCards()) {
      takeAction(actionCard);
    }
  }
}
