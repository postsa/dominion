package test.cards;

import main.cards.PlayerHand;
import main.game.Cards;
import main.game.Hand;
import org.junit.Before;
import org.junit.Test;
import test.cards.mocks.MockCardRegistry;

import static org.junit.Assert.assertEquals;

public class PlayerHandTest {

    private Hand hand;

    @Before
    public void setup() {
        hand = new PlayerHand(new MockCardRegistry());
    }

    @Test
    public void canAddCardToHand() {
        hand.addCardByName("Costs One");
        assertEquals(1, hand.countCardsWithName("Costs One"));
    }

    @Test
    public void takingOnlyCardOfNameFromHandResultsInZero() {
        hand.addCardByName("Costs One");
        hand.takeCardsFromHandByName("Costs One");
        assertEquals(0, hand.countCardsWithName("Costs One"));
    }

    @Test
    public void takingOneOfTwoCardOfNameFromHandResultsInOne() {
        hand.addCardByName("Costs One", 2);
        hand.takeCardsFromHandByName("Costs One");
        assertEquals(1, hand.countCardsWithName("Costs One"));
    }

    @Test
    public void takingTwoOfTwoCardOfNameFromHandResultsInZero() {
        hand.addCardByName("Costs One", 2);
        hand.takeCardsFromHandByName("Costs One", 2);
        assertEquals(0, hand.countCardsWithName("Costs One"));
    }

    @Test
    public void takingCardsFromHandResultsInCards() {
        hand.addCardByName("Costs One", 2);
        Cards result = hand.takeCardsFromHandByName("Costs One", 2);
        assertEquals(2, result.count());
    }
}
