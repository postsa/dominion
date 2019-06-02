package main.cards;

import main.cards.exceptions.CardNotInStock;
import main.game.CardRegistry;
import main.game.Cards;
import main.game.Inventory;
import main.game.exceptions.NoSuchCardAvaliable;

import java.util.Set;

public class CardInventory implements Inventory {

    CardRegistry cardRegistry;
    protected Cards inventory;

    public CardInventory(CardRegistry cardRegistry) {
        this.cardRegistry = cardRegistry;
        inventory = new Cards();
    }

    public Cards requestCard(String name) {
        Cards requestedCards = takeCardsByName(name);
        if(requestedCards.count() == 0)
            throw new CardNotInStock();
        return requestedCards;
    }

    public void addCardByName(String name) {
        CardType type = cardRegistry.lookupCardType(name);
        switch (type) {
            case ACTION:
                inventory.add(cardRegistry.getActionCards().get(name));
                break;
            case TREASURE:
                inventory.add(cardRegistry.getTreasureCards().get(name));
                break;
            case VICTORY:
                inventory.add(cardRegistry.getVictoryCards().get(name));
                break;
        }
    }

    public Cards takeCardsByName(String name) {
        CardType type = cardRegistry.lookupCardType(name);
        Cards cards = new Cards();
        switch (type) {
            case ACTION:
                cards.put(inventory.take(cardRegistry.getActionCards().get(name)));
                break;
            case TREASURE:
                cards.put(inventory.take(cardRegistry.getTreasureCards().get(name)));
                break;
            case VICTORY:
                cards.put(inventory.take(cardRegistry.getVictoryCards().get(name)));
                break;
        }
        return cards;
    }

    public int countCardsWithName(String cardName) {
        if(!cardRegistry.cardRegistered(cardName))
            throw new NoSuchCardAvaliable();
        return this.inventory.count(cardName);
    }

    @Override
    public void addCardByName(String cardName, int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException();
        for(int i = 0; i < quantity; i++)
            addCardByName(cardName);
    }

    @Override
    public Cards getInventory() {
        return this.inventory;
    }

    @Override
    public Set<String> requestAvailableCardNames() {
        return cardRegistry.requestAvailableCardNames();
    }
}
