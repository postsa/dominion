package main.game;

import main.game.commands.*;
import main.game.exceptions.CannotPlayVictoryCard;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private Hand hand;
  private DeckOfCards discard;
  private DeckOfCards deck;
  private List<Turn> turns;
  private Turn currentTurn;
  private CardMerchant cardMerchant;
  private String name;

  public Player(String name, CardMerchant cardMerchant) {
    this.name = name;
    this.cardMerchant = cardMerchant;
    this.deck = cardMerchant.getStartingDeck().shuffle();
    this.hand = cardMerchant.createHand();
    this.discard = new DeckOfCards();
    this.turns = new ArrayList<>();
    this.currentTurn = null;
  }

  public void startTurn(Turn turn) {
    this.drawHand();
    this.currentTurn = turn;
    this.turns.add(turn);
  }

  public void playTreasure(TreasureCard treasure) {
    Command playTreasure = new PlayTreasure(treasure);
    this.currentTurn.accept(playTreasure);
  }

  public void buyCard(String cardName) {
    Cards purchasedCards = new Cards();
    Purchasable cardInfo = cardMerchant.requestCardInfo(cardName);

    Command buyCard = new BuyCard(cardInfo);
    this.currentTurn.accept(buyCard);

    Cards cardToBuy = cardMerchant.requestCard(cardInfo.getName());
    purchasedCards.put(cardToBuy);
    this.addCardsToDiscard(purchasedCards);
  }

  public void takeAction(ActionCard actionCard) {
    Command takeAction = new TakeAction(actionCard);
    this.currentTurn.accept(takeAction);
  }

  public void playCards(Cards cards) {
    if (!cards.getVictoryCards().empty()) {
      throw new CannotPlayVictoryCard("Victory cards cannot be played.");
    }
    for (TreasureCard treasureCard : cards.getTreasure()) {
      this.playTreasure(treasureCard);
    }
    for (ActionCard actionCard : cards.getActionCards()) {
      this.takeAction(actionCard);
    }
  }

  public Hand getHand() {
    return hand;
  }

  public void addCardsToHand(Cards cards) {
    this.hand.put(cards);
  }

  public Cards takeCardsFromHand(String... names) {
    return this.hand.takeCards(names);
  }

  public void discardHand() {
    discard.put(this.hand.getInventory());
  }

  public void drawHand() {
    for (int i = 0; i < 5; i++) this.drawCard();
  }

  public void drawCard() {
    if (this.deck.count() == 0) this.deck.put(this.discard.shuffle());
    this.hand.put(this.deck.draw(1));
  }

  public DeckOfCards getDeck() {
    return this.deck;
  }

  public DeckOfCards getDiscard() {
    return this.discard;
  }

  public void addCardsToDiscard(Cards cards) {
    this.discard.put(cards);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void advancePhase() {
    switch (this.currentTurn.getPhase()) {
      case ACTION:
        this.currentTurn.accept(new StartBuyPhase());
        break;
      case BUY:
        this.discardHand();
        this.drawHand();
        this.currentTurn.accept(new StartCleanupPhase());
        break;
      case CLEAN_UP:
        this.currentTurn.accept(new EndTurn());
        break;
    }
  }
}
