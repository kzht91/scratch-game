package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Config {
    private int columns;
    private int rows;
    private Map<String, SymbolConfig> symbols;
    private Probability probabilities;
    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations;

    public void validate() {
        if (probabilities.getStandardSymbols().size() != columns * rows) {
            throw new IllegalStateException("Wrong columns or/and rows count");
        }
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Map<String, SymbolConfig> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, SymbolConfig> symbols) {
        this.symbols = symbols;
    }

    public Probability getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(Probability probabilities) {
        this.probabilities = probabilities;
    }

    public Map<String, WinCombination> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }
}

