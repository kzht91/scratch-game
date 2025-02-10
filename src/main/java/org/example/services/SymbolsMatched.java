package org.example.services;

public class SymbolsMatched {
    String symbol;
    String combination;
    String group;
    double rewardMultiplier;

    public SymbolsMatched(String symbol, String combination, String group, double rewardMultiplier) {
        this.symbol = symbol;
        this.combination = combination;
        this.group = group;
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCombination() {
        return combination;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }
}