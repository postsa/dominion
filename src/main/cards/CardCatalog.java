package main.cards;

import main.cards.exceptions.AlreadyRegistered;
import main.game.ActionCard;
import main.game.CardRegistry;
import main.game.TreasureCard;
import main.game.VictoryCard;
import main.game.exceptions.NoSuchCardAvaliable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CardCatalog implements CardRegistry {

    private Map<String, CardType> cardRegistry;
    private Map<String, ActionCard> actionCards;
    private Map<String, VictoryCard> victoryCards;
    private Map<String, TreasureCard> treasureCards;

    public CardCatalog() {
        this.cardRegistry = new HashMap<>();
        this.actionCards = new HashMap<>();
        this.victoryCards = new HashMap<>();
        this.treasureCards = new HashMap<>();
    }

    public Map<String, ActionCard> getActionCards() {
        return actionCards;
    }

    public Map<String, VictoryCard> getVictoryCards() {
        return victoryCards;
    }

    public Map<String, TreasureCard> getTreasureCards() {
        return treasureCards;
    }

    private void registerWithTypeIndex(String name, CardType cardType) {
        if (cardRegistry.containsKey(name))
            throw new AlreadyRegistered();
        this.cardRegistry.put(name, cardType);
    }

    public void registerCard(ActionCard card) {
        registerWithTypeIndex(card.getName(), CardType.ACTION);
        this.actionCards.put(card.getName(), card);
    }

    public void registerCard(VictoryCard card) {
        registerWithTypeIndex(card.getName(), CardType.VICTORY);
        this.victoryCards.put(card.getName(), card);
    }

    public void registerCard(TreasureCard card) {
        registerWithTypeIndex(card.getName(), CardType.TREASURE);
        this.treasureCards.put(card.getName(), card);
    }

    public boolean cardRegistered(String cardName) {
        return this.cardRegistry.containsKey(cardName);
    }

    public Set<String> requestAvailableCardNames() {
        return this.cardRegistry.keySet();
    }

    public CardType lookupCardType(String cardName) {
        if (!cardRegistered(cardName))
            throw new NoSuchCardAvaliable();
        return cardRegistry.get(cardName);
    }
}
