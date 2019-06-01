package main.cards;

import main.cards.exceptions.AlreadyRegistered;
import main.cards.exceptions.CardNotInStock;
import main.game.exceptions.NoSuchCardAvaliable;
import main.game.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Supply implements CardMerchant {

    private Map<String, Purchasable> purchasableCards;
    private Map<String, ActionCard> actionCards;
    private Map<String, VictoryCard> victoryCards;
    private Map<String, TreasureCard> treasureCards;
    private Map<String, CardType> cardRegistry;
    private Cards inventory = new Cards();

    public Supply() {
        this.purchasableCards = new HashMap<>();
        this.actionCards = new HashMap<>();
        this.victoryCards = new HashMap<>();
        this.treasureCards = new HashMap<>();
        this.cardRegistry = new HashMap<>();
    }

    private void registerWithTypeIndex(String name, CardType cardType) {
        if (cardRegistry.containsKey(name))
            throw new AlreadyRegistered();
        this.cardRegistry.put(name, cardType);
    }

    public void registerCard(ActionCard card) {
        registerWithTypeIndex(card.getName(), CardType.ACTION);
        this.actionCards.put(card.getName(), card);
        registerPurchasable(card);
    }

    public void registerCard(VictoryCard card) {
        registerWithTypeIndex(card.getName(), CardType.VICTORY);
        this.victoryCards.put(card.getName(), card);
        registerPurchasable(card);
    }

    public void registerCard(TreasureCard card) {
        registerWithTypeIndex(card.getName(), CardType.TREASURE);
        this.treasureCards.put(card.getName(), card);
        registerPurchasable(card);
    }

    private void registerPurchasable(Purchasable purchasable) {
        this.purchasableCards.put(purchasable.getName(), purchasable);
    }

    public Cards requestCard(String name) {
        Cards requestedCards = takeCardsFromInventoryByName(name);
        if(requestedCards.count() == 0)
            throw new CardNotInStock();
        return requestedCards;
    }

    private void addCardsToInventoryByName(String name) {
        CardType type = lookupCardType(name);
        switch (type) {
            case ACTION:
                inventory.add(actionCards.get(name));
                break;
            case TREASURE:
                inventory.add(treasureCards.get(name));
                break;
            case VICTORY:
                inventory.add(victoryCards.get(name));
                break;
        }
    }

    private Cards takeCardsFromInventoryByName( String name) {
        CardType type = lookupCardType(name);
        Cards cards = new Cards();
        switch (type) {
            case ACTION:
                cards.put(inventory.take(actionCards.get(name)));
                break;
            case TREASURE:
                cards.put(inventory.take(treasureCards.get(name)));
                break;
            case VICTORY:
                cards.put(inventory.take(victoryCards.get(name)));
                break;
        }
        return cards;
    }

    public Purchasable requestCardInfo(String cardName) {
        Purchasable cardInfo = purchasableCards.get(cardName);
        if (cardInfo == null)
            throw new NoSuchCardAvaliable();
        return cardInfo;
    }

    public Set<String> requestAvailableCardNames() {
        return this.cardRegistry.keySet();
    }

    private boolean cardRegistered(String cardName) {
        return this.cardRegistry.containsKey(cardName);
    }

    public int getInventoryOf(String cardName) {
        if(!cardRegistered(cardName))
            throw new NoSuchCardAvaliable();
        return this.inventory.count(cardName);
    }

    private CardType lookupCardType(String cardName) {
        if(!cardRegistered(cardName))
            throw new NoSuchCardAvaliable();
        return cardRegistry.get(cardName);
    }

    public void addInventory(String cardName) {
        addCardsToInventoryByName(cardName);
    }

    public void addInventory(String cardName, int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException();
        for(int i = 0; i < quantity; i++)
            addCardsToInventoryByName(cardName);
    }

    public Cards getInventory() {
        return this.inventory;
    }

    private enum CardType {
        ACTION,
        VICTORY,
        TREASURE
    }
}
