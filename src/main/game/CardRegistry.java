package main.game;

import main.cards.CardType;

import java.util.Map;
import java.util.Set;

public interface CardRegistry {
  void registerCard(ActionCard card);

  void registerCard(TreasureCard card);

  void registerCard(VictoryCard card);

  Map<String, ActionCard> getActionCards();

  Map<String, TreasureCard> getTreasureCards();

  Map<String, VictoryCard> getVictoryCards();

  boolean cardRegistered(String cardName);

  CardType lookupCardType(String cardName);

  Set<String> requestAvailableCardNames();
}
