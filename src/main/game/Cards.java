package main.game;

import java.util.Optional;
import java.util.Stack;

public class Cards {

    protected Stack<TreasureCard> treasure;
    protected Stack<ActionCard> actionCards;
    protected Stack<VictoryCard> victory;

    public Cards() {
        this.treasure = new Stack<>();
        this.actionCards = new Stack<>();
        this.victory = new Stack<>();
    }

    public Stack<TreasureCard> getTreasure() {
        return treasure;
    }

    public Stack<ActionCard> getActionCards() {
        return actionCards;
    }

    public Stack<VictoryCard> getVictoryCards() {
        return victory;
    }

    public void add(TreasureCard treasureCard) {
        this.treasure.push(treasureCard);
    }

    public void add(ActionCard actionCard) {
        this.actionCards.push(actionCard);
    }

    public void add(VictoryCard victoryCard) {
        this.victory.push(victoryCard);
    }

    public int count() {
        return this.victory.size() + this.treasure.size() + this.actionCards.size();
    }

    public int count(String cardName) {
        long count = this.victory.stream().filter(c -> cardName.equals(c.getName())).count() +
                this.treasure.stream().filter(c -> cardName.equals(c.getName())).count() +
                this.actionCards.stream().filter(c -> cardName.equals(c.getName())).count();
        return (int) count;
    }

    public void put(Cards otherCards) {
        while (!otherCards.getTreasure().empty())
            this.treasure.push(otherCards.getTreasure().pop());
        while (!otherCards.getVictoryCards().empty())
            this.victory.push(otherCards.getVictoryCards().pop());
        while (!otherCards.getActionCards().empty())
            this.actionCards.push(otherCards.getActionCards().pop());
    }

    public Cards take(ActionCard actionCard) {
        Cards takenCards = new Cards();
        Optional<ActionCard> anyActionCard = this.actionCards.stream()
                .filter(c -> actionCard.getName().equals(c.getName())).findFirst();
        if (anyActionCard.isPresent()) {
            this.actionCards.remove(anyActionCard.get());
            takenCards.add(anyActionCard.get());
        }
        return takenCards;
    }

    public Cards take(TreasureCard treasureCard) {
        Cards takenCards = new Cards();
        Optional<TreasureCard> anyTreasureCard = this.treasure.stream()
                .filter(c -> treasureCard.getName().equals(c.getName())).findFirst();
        if (anyTreasureCard.isPresent()) {
            this.treasure.remove(anyTreasureCard.get());
            takenCards.add(anyTreasureCard.get());
        }
        return takenCards;
    }

    public Cards take(VictoryCard victoryCard) {
        Cards takenCards = new Cards();
        Optional<VictoryCard> anyVictoryCard = this.victory.stream()
                .filter(c -> victoryCard.getName().equals(c.getName())).findFirst();
        if (anyVictoryCard.isPresent()) {
            this.victory.remove(anyVictoryCard.get());
            takenCards.add(anyVictoryCard.get());
        }
        return takenCards;
    }

    protected enum CardType {
        ACTION,
        VICTORY,
        TREASURE
    }
}
