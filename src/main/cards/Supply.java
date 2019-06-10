package main.cards;

import main.game.exceptions.NoSuchCardAvaliable;
import main.game.*;
import test.cards.cardtypes.Copper;
import test.cards.cardtypes.Estate;

import java.util.HashMap;
import java.util.Map;

public class Supply extends CardInventory implements CardMerchant {

    private Map<String, Purchasable> purchasableCards;

    public Supply(CardRegistry cardRegistry) {
        super(cardRegistry);
        this.purchasableCards = new HashMap<>();
        this.registerCatalogAsPurchasable();
    }

    private void registerCatalogAsPurchasable() {
        for (String key : cardRegistry.getVictoryCards().keySet()) {
            registerPurchasable(cardRegistry.getVictoryCards().get(key));
        }
        for (String key : cardRegistry.getTreasureCards().keySet()) {
            registerPurchasable(cardRegistry.getTreasureCards().get(key));
        }
        for (String key : cardRegistry.getActionCards().keySet()) {
            registerPurchasable(cardRegistry.getActionCards().get(key));
        }
    }

    private void registerPurchasable(Purchasable purchasable) {
        this.purchasableCards.put(purchasable.getName(), purchasable);
    }

    public Purchasable requestCardInfo(String cardName) {
        Purchasable cardInfo = purchasableCards.get(cardName);
        if (cardInfo == null)
            throw new NoSuchCardAvaliable();
        return cardInfo;
    }

    public DeckOfCards getStartingDeck() {
        DeckOfCards startingDeck = new DeckOfCards();
        Estate estate = new Estate();
        Copper copper = new Copper();
        for(int i = 0; i < 3; i++)
            startingDeck.add(estate);
        for(int i = 0; i < 7; i++)
            startingDeck.add(copper);
        return startingDeck;
    }

    public Hand createHand() {
        return new PlayerHand(cardRegistry);
    }
}
