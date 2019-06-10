package test.cards;

import main.cards.CardCatalog;
import main.cards.Supply;
import main.game.DeckOfCards;
import main.game.Purchasable;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.CostsOne;

import static junit.framework.TestCase.*;

public class SupplyTest {

    private Supply supply;
    private CostsOne costsOne;

    @Before
    public void setup() {
        CardCatalog cardCatalog = new CardCatalog(); //replace with a mock
        costsOne = new CostsOne();
        cardCatalog.registerCard(costsOne);
        supply = new Supply(cardCatalog);
    }

    @Test
    public void canRequestRegisteredCardInfoFromSupply() {
        Purchasable p = supply.requestCardInfo(costsOne.getName());
        assertEquals(costsOne.getName(), p.getName());
        assertEquals(costsOne.getCost(), p.getCost());
    }

    @Test
    public void startingDeckNotNullOrEmpty() {
        DeckOfCards startingDeck = supply.getStartingDeck();
        assertNotNull(startingDeck);
        assertTrue(startingDeck.count() > 0);
    }
}
