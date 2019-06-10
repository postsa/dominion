package main.game;

import main.game.controller.Controller;
import main.game.controller.PlayerInput;
import main.game.exceptions.*;

import static main.game.Phase.*;

public class Game {
    private Player player;
    private boolean isOver;
    private int money;
    private int actionsRemaining;
    private int buysRemaining;
    private Phase phase;
    private CardMerchant cardMerchant;
    private Presenter presenter;
    private Controller controller;

    public Game(CardMerchant cardMerchant, Presenter presenter, Controller controller) {
        this.actionsRemaining = 1;
        this.buysRemaining = 1;
        this.money = 0;
        this.cardMerchant = cardMerchant;
        this.player = new Player(cardMerchant.createHand(), cardMerchant.getStartingDeck().shuffle());
        this.player.drawHand();
        this.phase = Phase.ACTION;
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
        return this.phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public int getMoney() {
        return money;
    }

    public Cards buyCard(String cardName) {
        throwIfNotInBuyPhase();
        Cards purchasedCards = new Cards();
        Purchasable cardInfo = cardMerchant.requestCardInfo(cardName);
        if (notEnoughMoneyToBuy(cardInfo))
            throw new NotEnoughMoneyToBuyCard();
        if(noBuysRemaining())
            throw new NoBuysRemaining();
        purchasedCards.put(cardMerchant.requestCard(cardInfo.getName()));
        this.money -= cardInfo.getCost();
        this.buysRemaining--;
        return purchasedCards;
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

    public void addMoney(int amount) {
        this.money += amount;
    }

    private void displaySupply() {
        presenter.displayCardsForSale(this.cardMerchant);
    }

    private void displayHand() {
        presenter.displayHand(player.getHand());
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
                advancePhase();
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
        this.player.discardHand();
        this.player.drawHand();
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
