package org.example.services;

import org.example.models.Config;
import org.example.models.Result;
import org.example.models.SymbolConfig;

import java.util.*;

public class RewardCalculator {

    private final Config config;
    private Map<String, List<String>> winningCombinations;
    private Map<String, Double> rewardMultipliers;

    public RewardCalculator(Config config) {
        this.config = config;
    }

    public Result calculate(String[][] matrix, int betAmount) {
        double reward = 0;

        Matcher matcher = new Matcher(config.getWinCombinations());
        List<SymbolsMatched> matched = new ArrayList<>();
        matched.addAll(matcher.matchSameSymbols(matrix));
        matched.addAll(matcher.matchLinearSymbols(matrix));

        String appliedBonusSymbol = null;
        if (!matched.isEmpty()) {
            applyMultipliers(matched);
            reward = applyBasicSymbols(rewardMultipliers, betAmount);
            appliedBonusSymbol = applyBonusSymbol(matrix);
            reward = applyBonusSymbol(appliedBonusSymbol, reward);
        }

        return new Result(matrix, reward, winningCombinations, appliedBonusSymbol);
    }

    private void applyMultipliers(List<SymbolsMatched> matched) {
        winningCombinations = new HashMap<>();
        rewardMultipliers = new HashMap<>();
        Set<String> covered = new HashSet<>();
        for (SymbolsMatched symbolsMatched : matched) {
            String key = symbolsMatched.getSymbol() + ":" + symbolsMatched.getGroup();

            if (covered.add(key)) {
                winningCombinations
                        .computeIfAbsent(symbolsMatched.getSymbol(), k -> new ArrayList<>())
                        .add(symbolsMatched.getCombination());

                rewardMultipliers.merge(
                        symbolsMatched.getSymbol(),
                        symbolsMatched.getRewardMultiplier(),
                        (existing, newMultiplier) -> existing * newMultiplier
                );
            }
        }
    }

    private double applyBasicSymbols(Map<String, Double> rewardMultipliers, double reward) {
        double rewardMultiplier = 0;
        for (Map.Entry<String, Double> entry : rewardMultipliers.entrySet()) {
            double symbolMultiplier = config.getSymbols().get(entry.getKey()).getRewardMultiplier();
            rewardMultiplier += symbolMultiplier * entry.getValue();
        }
        assert rewardMultiplier > 0 : "Multiplier should be greater than zero.";
        return reward * rewardMultiplier;
    }

    private double applyBonusSymbol(String bonusSymbol, double reward) {
        if (bonusSymbol != null) {
            SymbolConfig bonus = config.getSymbols().get(bonusSymbol);
            switch (bonus.getImpact()) {
                case "multiply_reward":
                    return reward * bonus.getRewardMultiplier();
                case "extra_bonus":
                    return reward + bonus.getExtra();
                case "miss":
            }
        }
        return reward;
    }

    private String applyBonusSymbol(String[][] matrix) {
        for (String[] strings : matrix) {
            for (String symbol : strings) {
                // Check if this symbol is a bonus symbol (example "10x", "5x", "+1000", "+500")
                if (config.getProbabilities().getBonusSymbols().getSymbols().containsKey(symbol)) {
                    return symbol; // Return the bonus symbol found in the matrix
                }
            }
        }
        return "MISS"; // If no bonus symbol is found
    }
}
