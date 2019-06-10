package test.game.mocks;

import main.game.*;

public class MockPresenter implements Presenter {
    public void displayCardsForSale(CardMerchant inventory) { }

    public void displayHand(Hand hand) { }

    public void displayAvailableMoney(int availableMoney) { }

    public void displayPhase(Phase phase) { }

    public void displayBuysRemaining(int buysRemaining) { }

    public void displayActionsRemaining(int actionsRemaining) { }

    public void displayInputError(Exception ex) { }
}
