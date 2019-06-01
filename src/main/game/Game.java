package main.game;

import main.game.exceptions.*;

import static main.game.Phase.*;

public class Game {
    private int money;
    private int actionsRemaining;
    private int buysRemaining;
    private DeckOfCards deck;
    private Cards hand;
    private DeckOfCards discard;
    private CardMerchant cardMerchant;
    private Phase phase;
    private Presentor presentor;

    public Game(CardMerchant cardMerchant, Presentor presentor) {
        this.actionsRemaining = 1;
        this.buysRemaining = 1;
        this.money = 0;
        this.hand = new Cards();
        this.deck = new DeckOfCards();
        this.discard = new DeckOfCards();
        this.cardMerchant = cardMerchant;
        this.phase = Phase.ACTION;
        this.presentor = presentor;
    }

    public Phase getPhase() {
        return this.phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Cards getHand() {
        return hand;
    }

    public DeckOfCards getDeck() {
        return this.deck;
    }

    public DeckOfCards getDiscard() {
        return this.discard;
    }

    public int getMoney() {
        return money;
    }

    public void buyCard(String cardName) {
        throwIfNotInBuyPhase();
        Purchasable cardInfo = cardMerchant.requestCardInfo(cardName);
        if (notEnoughMoneyToBuy(cardInfo))
            throw new NotEnoughMoneyToBuyCard();
        this.hand.put(cardMerchant.requestCard(cardInfo.getName()));
        this.buysRemaining--;
        if(noBuysRemaining())
            this.setPhase(CLEAN_UP);
    }

    private void throwIfNotInBuyPhase() {
        if (this.phase != BUY)
            throw new GameNotInBuyPhase();
    }

    private boolean notEnoughMoneyToBuy(Purchasable cardInfo) {
        return this.money < cardInfo.getCost();
    }

    private boolean noBuysRemaining() {
        return this.buysRemaining <= 0;
    }

    public void playTreasure(TreasureCard treasure) {
        throwIfNotInBuyPhase();
        treasure.play(this);
    }

    public int getActionsRemaining() {
        return this.actionsRemaining;
    }

    public void addAction() {
        this.actionsRemaining++;
    }

    public int getBuysRemaining() {
        return this.buysRemaining;
    }

    public void addBuy() {
        this.buysRemaining++;
    }

    public void takeAction(ActionCard actionCard) {
        throwIfNotInActionPhase();
        actionCard.act(this);
        this.actionsRemaining--;
        if (noActionsRemaining()) {
            setPhase(BUY);
        }
    }

    private void throwIfNotInActionPhase() {
        if (this.phase != Phase.ACTION)
            throw new GameNotInActionPhase();
    }

    private boolean noActionsRemaining() {
        return this.actionsRemaining <= 0;
    }

    public void discardHand() {
        throwIfNotInCleanUpPhase();
        discard.put(this.hand);
    }

    public void drawHand() {
        throwIfNotInCleanUpPhase();
        for(int i = 0; i < 5; i++)
            drawCard();
    }

    private void throwIfNotInCleanUpPhase() {
        if(this.phase != CLEAN_UP)
            throw new GameNotInCleanUpPhase();
    }

    public void drawCard() {
        if (this.deck.count() == 0)
            this.deck.put(this.discard.shuffle());
        this.hand.put(this.deck.draw(1));
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public void listAvailableCards() {
        presentor.displayAvailableCards(cardMerchant.getInventory());
    }
}
