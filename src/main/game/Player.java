package main.game;

public class Player {
  private Hand hand;
  private DeckOfCards discard;
  private DeckOfCards deck;

  public Player(Hand hand, DeckOfCards startingDeck) {
    this.deck = startingDeck;
    this.hand = hand;
    this.discard = new DeckOfCards();
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
}
