package test.cards;

import main.game.Cards;
import main.game.DeckOfCards;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.Copper;
import test.cards.cardtypes.CostsOne;
import test.cards.cardtypes.Estate;
import test.cards.cardtypes.Silver;

import static org.junit.Assert.assertEquals;

public class DeckOfCardsTest {

    private DeckOfCards deck;
    private Estate estate;
    private Copper copper;
    private Silver silver;

    @Before
    public void setup() {
        estate = new Estate();
        copper = new Copper();
        silver = new Silver();
        deck = new DeckOfCards();
    }

    @Test
    public void canDrawCard() {
        deck.add(estate);
        Cards c = deck.draw(1);
        assertEquals(estate, c.getVictoryCards().get(0));
    }

    @Test
    public void canDrawTwoCardsFromCards() {
        deck.add(estate);
        deck.add(copper);
        Cards c = deck.draw(2);
        assertEquals(copper, c.getTreasure().get(0));
        assertEquals(estate, c.getVictoryCards().get(0));
    }

    @Test
    public void canDrawTopTwoOfThreeCards() {
        CostsOne costOne = new CostsOne();
        deck.add(costOne);
        deck.add(estate);
        deck.add(copper);
        Cards c = deck.draw(2);
        assertEquals(copper, c.getTreasure().get(0));
        assertEquals(estate, c.getVictoryCards().get(0));
        assertEquals(0, c.getActionCards().size());
    }

    @Test
    public void canDrawTwoDifferentTreasure() {
        deck.add(copper);
        deck.add(silver);
        Cards c = deck.draw(2);
        assertEquals(silver, c.getTreasure().get(0));
        assertEquals(copper, c.getTreasure().get(1));
    }

    @Test
    public void canOnlyDrawUpToTheNumberOfCardsInADeck() {
        deck.add(copper);
        deck.add(silver);
        deck.add(new CostsOne());
        deck.add(estate);
        Cards c = deck.draw(5);
        assertEquals(4, c.count());
    }

    @Test
    public void drawingCardsFromDeckRemovesThemFromDeck() {
        deck.add(copper);
        deck.add(silver);
        deck.add(new CostsOne());
        deck.add(estate);
        deck.draw(3);
        assertEquals(1, deck.count());
    }

    @Test
    public void puttingOtherDeckOntoDeckAddsToCardOrderStack() {
        DeckOfCards otherDeck = new DeckOfCards();
        otherDeck.add(copper);
        otherDeck.add(silver);
        otherDeck.add(new CostsOne());
        otherDeck.add(estate);
        deck.put(otherDeck);
        assertEquals(4, deck.getCardOrder().size());
    }

    @Test
    public void puttingCardsOntoDeckAddsToCardOrderStack() {
        Cards cards = new DeckOfCards();
        cards.add(copper);
        cards.add(silver);
        cards.add(new CostsOne());
        cards.add(estate);
        deck.put(cards);
        assertEquals(4, deck.getCardOrder().size());
    }

    @Test
    public void puttingOtherDeckOntoDeckRemovesAllFromOtherCardOrderStack() {
        DeckOfCards otherDeck = new DeckOfCards();
        otherDeck.add(copper);
        otherDeck.add(silver);
        otherDeck.add(new CostsOne());
        otherDeck.add(estate);
        deck.put(otherDeck);
        assertEquals(0, otherDeck.getCardOrder().size());
    }
}
