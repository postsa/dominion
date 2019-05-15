package main.game;

import java.util.Collections;
import java.util.Stack;

public class DeckOfCards extends Cards {

    private Stack<CardType> cardOrder;

    public DeckOfCards() {
        super();
        this.cardOrder = new Stack<>();
    }

    public void add(TreasureCard treasureCard) {
        super.add(treasureCard);
        this.cardOrder.push(CardType.TREASURE);
    }

    public void add(ActionCard actionCard) {
        super.add(actionCard);
        this.cardOrder.push(CardType.ACTION);
    }

    public void add(VictoryCard victoryCard) {
        super.add(victoryCard);
        this.cardOrder.push(CardType.VICTORY);
    }

    public Cards draw(int number) {
        Cards drawnCards = new Cards();
        while (number > 0 && !this.cardOrder.empty()) {
            CardType nextCardType = this.cardOrder.pop();
            switch (nextCardType) {
                case TREASURE:
                    drawnCards.add(this.treasure.pop());
                    break;
                case VICTORY:
                    drawnCards.add(this.victory.pop());
                    break;
                case ACTION:
                    drawnCards.add(this.actionCards.pop());
                    break;
            }
            number--;
        }
        return drawnCards;
    }

    public DeckOfCards shuffle() {
        Collections.shuffle(this.cardOrder);
        Collections.shuffle(this.treasure);
        Collections.shuffle(this.victory);
        Collections.shuffle(this.actionCards);
        return this;
    }

    public void put(DeckOfCards otherDeck) {
        super.put(otherDeck);
        while(!otherDeck.getCardOrder().empty())
            this.cardOrder.push(otherDeck.getCardOrder().pop());
    }

    public Stack<CardType> getCardOrder() {
        return this.cardOrder;
    }
}
