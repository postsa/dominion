package test.game;

import main.game.Purchasable;

public class MockPurchasable implements Purchasable {

    private int cost;
    private String name;

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public MockPurchasable withName(String name) {
        this.name = name;
        return this;
    }

    public MockPurchasable withCost(int cost) {
        this.cost = cost;
        return this;
    }
}
