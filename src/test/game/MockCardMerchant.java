package test.game;

import main.game.exceptions.NoSuchCardAvaliable;
import main.game.*;
import test.cards.cardtypes.CostsOne;
import test.cards.cardtypes.CostsZero;

public class MockCardMerchant implements CardMerchant {
    public Cards requestCard(String name) {
        Cards cards = new Cards();
        switch(name) {
            case "Costs Zero":
                cards.add(new CostsZero());
                break;
            case "Costs One":
                cards.add(new CostsOne());
                break;
        }
        return cards;
    }

    public Purchasable requestCardInfo(String cardName) {
        switch(cardName) {
            case "Costs Zero":
                return new MockPurchasable().withCost(0).withName(cardName);
            case "Costs One":
                return new MockPurchasable().withCost(1).withName(cardName);
            default:
                throw new NoSuchCardAvaliable();
        }
    }

    public void registerCard(ActionCard card) { }

    public void registerCard(VictoryCard card) { }

    public void registerCard(TreasureCard card) { }

    public Cards getInventory() {
        return null;
    }
}
