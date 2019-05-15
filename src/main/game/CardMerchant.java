package main.game;

public interface CardMerchant {
    Cards requestCard(String name);

    Purchasable requestCardInfo(String cardName);

    void registerCard(ActionCard card);

    void registerCard(VictoryCard card);

    void registerCard(TreasureCard card);
}
