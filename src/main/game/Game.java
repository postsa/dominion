package main.game;

import main.game.controller.Controller;
import main.game.controller.PlayerInput;
import main.game.exceptions.*;

import static main.game.Phase.*;

public class Game {
    private boolean isOver;
    private int money;
    private int actionsRemaining;
    private int buysRemaining;
    private DeckOfCards deck;
    private Hand hand;
    private DeckOfCards discard;
    private Phase phase;
    private CardMerchant cardMerchant;
    private Presenter presenter;
    private Controller controller;

    public Game(CardMerchant cardMerchant, Presenter presenter, Controller controller) {
        this.actionsRemaining = 1;
        this.buysRemaining = 1;
        this.money = 0;
        this.deck = cardMerchant.getStartingDeck().shuffle();
        this.cardMerchant = cardMerchant;
        this.discard = new DeckOfCards();
        this.createStartingHand();
        this.phase = Phase.ACTION;
        this.presenter = presenter;
        this.controller = controller;
    }

    private void createStartingHand() {
        this.phase = Phase.CLEAN_UP;
        this.hand = cardMerchant.createHand();
        this.drawHand();
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public Phase getPhase() {
        return this.phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Hand getHand() {
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
        if(noBuysRemaining())
            throw new NoBuysRemaining();
        this.hand.put(cardMerchant.requestCard(cardInfo.getName()));
        this.money -= cardInfo.getCost();
        this.buysRemaining--;
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
        if(noActionsRemaining())
            throw new NoActionsRemaining();
        actionCard.act(this);
        this.actionsRemaining--;
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
        discard.put(this.hand.getInventory());
    }

    public void drawHand() {
        throwIfNotInCleanUpPhase();
        for (int i = 0; i < 5; i++)
            drawCard();
    }

    private void throwIfNotInCleanUpPhase() {
        if (this.phase != CLEAN_UP)
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

    public void displaySupply() {
        presenter.displayCardsForSale(this.cardMerchant);
    }

    public void displayHand() {
        presenter.displayHand(this.getHand());
    }

    private void displayAvailableMoney() {
        presenter.displayAvailableMoney(this.money);
    }

    private void displayPhase() {
        presenter.displayPhase(this.getPhase());
    }

    private void displayBuysRemaining() {
        presenter.displayBuysRemaining(this.getBuysRemaining());
    }

    private void displayActionsRemaining() {
        presenter.displayActionsRemaining(this.getActionsRemaining());
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
        PlayerInput input = this.controller.acceptInput();
        this.processPlayerInput(input);
    }

    public void processPlayerInput(PlayerInput input) {
        switch (input.getInputAction()) {
            case QUIT:
                this.setOver(true);
                break;
            case ADVANCE_TURN:
                advancePhase();
                break;
            case BUY_CARD:
                this.buyCard(input.getCardName());
                break;
            case PLAY_CARD:
                Cards cardsToPlay = this.hand.takeCardsFromHandByName(input.getCardName());
                this.playCards(cardsToPlay);
                break;
        }
    }

    //needs tests
    public void advancePhase() {
        switch (this.phase) {
            case ACTION:
                putGameInBuyPhase();
                break;
            case BUY:
                putGameInCleanupPhase();
                break;
            case CLEAN_UP:
                putGameInActionPhase();
                break;
        }
    }

    private void putGameInActionPhase() {
        this.setPhase(ACTION);
        this.actionsRemaining = 1;
    }

    private void putGameInCleanupPhase() {
        this.setPhase(CLEAN_UP);
        this.money = 0;
        this.discardHand();
        this.drawHand();
    }

    private void putGameInBuyPhase() {
        this.setPhase(BUY);
        this.buysRemaining = 1;
    }

    public void playCards(Cards cards) {
        for (TreasureCard treasureCard : cards.getTreasure()) {
            playTreasure(treasureCard);
        }
        for (ActionCard actionCard : cards.getActionCards()) {
            takeAction(actionCard);
        }
    }
}
