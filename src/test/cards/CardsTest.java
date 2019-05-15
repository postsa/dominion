package test.cards;

import main.game.ActionCard;
import main.game.Cards;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.*;

import static org.junit.Assert.*;

public class CardsTest {

    private Cards cards;

    @Before
    public void setup() {
        cards = new Cards();
    }

    @Test
    public void canAddTreasureToCards() {
        Copper copper = new Copper();
        cards.add(copper);
        assertEquals(1, cards.getTreasure().size());
        assertEquals(copper, cards.getTreasure().get(0));
    }

    @Test
    public void canAddCardToCards() {
        ActionCard card = new CostsOne();
        cards.add(card);
        assertEquals(1, cards.getActionCards().size());
        assertEquals(card, cards.getActionCards().get(0));
    }

    @Test
    public void canAddLandToCards() {
        Estate estate = new Estate();
        cards.add(estate);
        assertEquals(1, cards.getVictoryCards().size());
        assertEquals(estate, cards.getVictoryCards().get(0));
    }

    @Test
    public void canPutCardsOntoOtherCards() {
        cards.add(new Estate());
        cards.add(new Copper());
        cards.add(new CostsOne());
        Cards otherCards = new Cards();
        otherCards.add(new Silver());
        otherCards.add(new AddsOneBuy());
        cards.put(otherCards);
        assertEquals(5, cards.count());
        assertEquals(2, cards.getTreasure().size());
        assertEquals(2, cards.getActionCards().size());
        assertEquals(1, cards.getVictoryCards().size());
    }

    @Test
    public void puttingOtherCardsOntoCardsEmptiesOtherCards() {
        Cards otherCards = new Cards();
        otherCards.add(new Silver());
        otherCards.add(new AddsOneBuy());
        cards.put(otherCards);
        assertEquals(0, otherCards.count());
    }

    @Test
    public void canCountCardsCorrectly() {
        CostsOne costsOne = new CostsOne();
        Estate estate = new Estate();
        cards.add(costsOne);
        cards.add(costsOne);
        cards.add(estate);
        assertEquals(2, cards.count(costsOne.getName()));
        assertEquals(1, cards.count(estate.getName()));
    }

    @Test
    public void canTakeCardFromCards() {
        CostsOne costsOne = new CostsOne();
        cards.add(costsOne);
        Cards otherCards = cards.take(costsOne);
        assertEquals(0, cards.count(costsOne.getName()));
        assertEquals(1, otherCards.count(costsOne.getName()));
    }

    @Test
    public void takingCardOnlyTakesOneOfMultipleCopies() {
        CostsOne costsOne = new CostsOne();
        cards.add(costsOne);
        cards.add(costsOne);
        Cards otherCards = cards.take(costsOne);
        assertEquals(1, cards.count(costsOne.getName()));
        assertEquals(1, otherCards.count(costsOne.getName()));
    }
}
