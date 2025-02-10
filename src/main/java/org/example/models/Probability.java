package org.example.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Probability {
    @JsonProperty("standard_symbols")
    private List<SymbolProbability> standardSymbols;

    @JsonProperty("bonus_symbols")
    private Symbols bonusSymbols;

    public List<SymbolProbability> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<SymbolProbability> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public Symbols getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(Symbols bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
