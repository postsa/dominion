package test.cards.mocks;

import main.cards.CardType;
import main.game.ActionCard;
import main.game.CardRegistry;
import main.game.TreasureCard;
import main.game.VictoryCard;
import main.game.exceptions.NoSuchCardAvaliable;
import test.cards.cardtypes.CostsOne;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static main.cards.CardType.ACTION;

public class MockCardRegistry implements CardRegistry {

    private Map<String, ActionCard> actionCards;

    public MockCardRegistry() {
        this.actionCards = new HashMap<>();
        actionCards.put("Costs One", new CostsOne());
    }

    public void registerCard(ActionCard card) {
        actionCards.put(card.getName(), card);
    }

    public void registerCard(TreasureCard card) {

    }

    public void registerCard(VictoryCard card) {

    }

    public Map<String, ActionCard> getActionCards() {
        return actionCards;
    }

    public Map<String, TreasureCard> getTreasureCards() {
        return null;
    }

    public Map<String, VictoryCard> getVictoryCards() {
        return null;
    }

    public boolean cardRegistered(String cardName) {
        return cardName.equals("Costs One");
    }

    public CardType lookupCardType(String cardName) {
        if (cardName == "Doesn't Exist")
            throw new NoSuchCardAvaliable();
        return ACTION;
    }

    public Set<String> requestAvailableCardNames() {
        return null;
    }
}
