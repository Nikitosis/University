package com.task1.work;

public class Function {
    private boolean isAllComponentsRequired;
    private int minComponentsRequired;
    private int reward;

    public Function(boolean isAllComponentsRequired, int reward) {
        this.isAllComponentsRequired = isAllComponentsRequired;
        this.reward = reward;
    }

    public Function(int minComponentsRequired, int reward) {
        this.minComponentsRequired = minComponentsRequired;
        this.reward = reward;
    }

    public boolean isAllComponentsRequired() {
        return isAllComponentsRequired;
    }

    public int getMinComponentsRequired() {
        return minComponentsRequired;
    }

    public int getReward() {
        return reward;
    }
}
