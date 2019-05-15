package test.cards;

import main.cards.exceptions.AlreadyRegistered;
import main.cards.exceptions.CardNotInStock;
import main.cards.Supply;
import main.game.exceptions.NoSuchCardAvaliable;
import main.game.Cards;
import main.game.Purchasable;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.CostsOne;
import test.cards.cardtypes.CostsZero;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.*;

public class SupplyTest {

    private Supply supply;
    private CostsOne costsOne;

    @Before
    public void setup() {
        supply = new Supply();
        costsOne = new CostsOne();
        supply.registerCard(costsOne);
    }

    @Test
    public void canRequestRegisteredCardInfoFromSupply() {
        Purchasable p = supply.requestCardInfo(costsOne.getName());
        assertEquals(costsOne.getName(), p.getName());
        assertEquals(costsOne.getCost(), p.getCost());
    }

    @Test
    public void canRequestRegisteredCardFromSupplyIfInStock() {
        supply.addInventory(costsOne.getName());
        Cards cards = supply.requestCard(costsOne.getName());
        assertEquals(1, cards.count());
        assertEquals(costsOne, cards.getActionCards().get(0));
    }

    @Test(expected = AlreadyRegistered.class)
    public void cannotRegisterTheSameCardTwice() {
        supply.registerCard(costsOne);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotAddNegativeQuantityToInventory() {
        supply.addInventory(costsOne.getName(), -1);
    }

    @Test(expected = NoSuchCardAvaliable.class)
    public void cannotAddInventoryIfCardNotRegistered() {
        supply.addInventory("Doesn't Exist");
    }

    @Test
    public void addingCardToInventoryResultsInAdditionalInventory() {
        supply.addInventory(costsOne.getName());
        assertEquals(1, supply.getInventoryOf(costsOne.getName()));
    }

    @Test
    public void addingCardToInventoryWithQuantityIsReflectedInSupply() {
        supply.addInventory(costsOne.getName(), 2);
        assertEquals(2, supply.getInventoryOf(costsOne.getName()));
    }
    
    @Test
    public void cardInventoryIsSpecificToTheCardName() {
        CostsZero costsZero = new CostsZero();
        supply.addInventory(costsOne.getName(), 2);
        supply.registerCard(costsZero);
        supply.addInventory(costsZero.getName());
        assertEquals(2, supply.getInventoryOf(costsOne.getName()));
    }

    @Test(expected = CardNotInStock.class)
    public void cannotRequestCardWithNoInventory() {
        supply.requestCard(costsOne.getName());
    }

    @Test
    public void canRequestAvailableCardNames() {
        CostsZero costsZero = new CostsZero();
        supply.registerCard(costsZero);
        Set<String> expectedNames = new HashSet<>();
        expectedNames.add(costsOne.getName());
        expectedNames.add(costsZero.getName());
        Set<String> names = supply.requestAvailableCardNames();
        assertTrue(names.containsAll(expectedNames));
        assertTrue(expectedNames.containsAll(names));
    }
}
