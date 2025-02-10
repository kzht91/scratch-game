package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Result {
    String[][] matrix;
    double reward;
    @JsonProperty("applied_winning_combinations")
    Map<String, List<String>> appliedWinningCombinations;
    @JsonProperty("applied_bonus_symbol")
    String appliedBonusSymbol;

    public Result(
            String[][] matrix,
            double reward,
            Map<String, List<String>> appliedWinningCombinations,
            String appliedBonusSymbol
    ) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public void setAppliedWinningCombinations(Map<String, List<String>> appliedWinningCombinations) {
        this.appliedWinningCombinations = appliedWinningCombinations;
    }

    public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }

    public void setAppliedBonusSymbol(String appliedBonusSymbol) {
        this.appliedBonusSymbol = appliedBonusSymbol;
    }
}