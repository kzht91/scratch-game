package org.example.services;

import org.example.models.Config;
import org.example.models.SymbolProbability;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MatrixGenerator {

    private final Config config;
    private final Random random = new Random();

    public MatrixGenerator(Config config) {
        this.config = config;
    }

    public String[][] generateMatrix() {
        String[][] matrix = new String[config.getRows()][config.getColumns()];
        boolean isBonusSet = false;
        for (int row = 0; row < config.getRows(); row++) {
            for (int col = 0; col < config.getColumns(); col++) {
                if (!isBonusSet && random.nextBoolean()) {
                    matrix[row][col] = getRandomBonusSymbol();
                    isBonusSet = true;
                } else {
                    matrix[row][col] = getRandomSymbol();
                }
            }
        }
        return matrix;
    }

    private String getRandomSymbol() {
        List<SymbolProbability> standardSymbols = config.getProbabilities().getStandardSymbols();
        int totalProbability = standardSymbols.stream()
                .mapToInt(sp -> sp.getSymbols().values().stream().mapToInt(Integer::intValue).sum())
                .sum();
        int randomValue = random.nextInt(totalProbability);

        for (SymbolProbability sp : standardSymbols) {
            for (Map.Entry<String, Integer> entry : sp.getSymbols().entrySet()) {
                if (randomValue < entry.getValue()) {
                    return entry.getKey();
                }
                randomValue -= entry.getValue();
            }
        }
        throw new IllegalStateException("No symbol selected. The probabilities might be incorrect.");
    }

    private String getRandomBonusSymbol() {
        Map<String, Integer> bonusSymbols = config.getProbabilities().getBonusSymbols().getSymbols();
        int totalProbability = bonusSymbols.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalProbability);

        for (Map.Entry<String, Integer> entry : bonusSymbols.entrySet()) {
            if (randomValue < entry.getValue()) {
                return entry.getKey();
            }
            randomValue -= entry.getValue();
        }
        throw new IllegalStateException("No symbol selected. The probabilities might be incorrect.");
    }
}
