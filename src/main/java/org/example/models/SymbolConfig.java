package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SymbolConfig {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    private String type;
    private Double extra; // Only for bonus symbols
    private String impact; // Only for bonus symbols

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getExtra() {
        return extra;
    }

    public void setExtra(Double extra) {
        this.extra = extra;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}
