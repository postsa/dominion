package test.cards;

import main.cards.CardCatalog;
import main.cards.exceptions.AlreadyRegistered;
import org.junit.Before;
import org.junit.Test;
import test.cards.cardtypes.CostsOne;
import test.cards.cardtypes.CostsZero;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class CardCatalogTest {

  private CardCatalog cardCatalog;
  private CostsOne costsOne;

  @Before
  public void setup() {
    this.costsOne = new CostsOne();
    this.cardCatalog = new CardCatalog();
    this.cardCatalog.registerCard(this.costsOne);
  }

  @Test(expected = AlreadyRegistered.class)
  public void cannotRegisterTheSameCardTwice() {
    cardCatalog.registerCard(costsOne);
  }

  @Test
  public void canRequestAvailableCardNames() {
    CostsZero costsZero = new CostsZero();
    cardCatalog.registerCard(costsZero);
    Set<String> expectedNames = new HashSet<>();
    expectedNames.add(costsOne.getName());
    expectedNames.add(costsZero.getName());
    Set<String> names = cardCatalog.requestAvailableCardNames();
    assertTrue(names.containsAll(expectedNames));
    assertTrue(expectedNames.containsAll(names));
  }
}
