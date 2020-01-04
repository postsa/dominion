package main.game;

import main.game.controller.Controller;
import main.game.controller.PlayerInput;

import java.util.ArrayList;
import java.util.List;

public class Game {
  private Player currentPlayer;
  private boolean isOver;
  private Presenter presenter;
  private Controller controller;
  private List<Player> players;
  private int currentPlayerIndex;
  private List<Turn> turns;
  private Turn turn;
  private CardMerchant cardMerchant;

  public Game(
      List<Player> players, CardMerchant cardMerchant, Presenter presenter, Controller controller) {
    this.currentPlayerIndex = -1;
    this.players = players;
    this.presenter = presenter;
    this.controller = controller;
    this.turns = new ArrayList<>();
    this.startNextTurn();
    this.cardMerchant = cardMerchant;
  }

  private void startNextTurn() {
    this.turn = new Turn();
    this.turns.add(this.turn);
    if (this.currentPlayerIndex < this.players.size() - 1) this.currentPlayerIndex++;
    else this.currentPlayerIndex = 0;
    this.currentPlayer = this.players.get(currentPlayerIndex);
    this.currentPlayer.startTurn(this.turn);
  }

  public boolean isOver() {
    return isOver;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  private void setOver(boolean over) {
    isOver = over;
  }

  public Phase getPhase() {
    return turn.getPhase();
  }

  private void displayPlayerName() {
    presenter.displayPlayerName(this.currentPlayer.getName());
  }

  private void displaySupply() {
    presenter.displayCardsForSale(this.cardMerchant);
  }

  private void displayHand() {
    presenter.displayHand(currentPlayer.getHand());
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
    this.displayPlayerName();
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
        this.advanceTurn();
        break;
      case BUY_CARD:
        this.currentPlayer.buyCard(input.getCardName());
        break;
      case PLAY_CARD:
        Cards cardsToPlay = this.currentPlayer.takeCardsFromHand(input.getCardName());
        this.currentPlayer.playCards(cardsToPlay);
        break;
    }
  }

  public void advanceTurn() {
    this.currentPlayer.advancePhase();
    if (this.turn.getPhase() == Phase.OVER) {
      this.startNextTurn();
    }
  }
}
