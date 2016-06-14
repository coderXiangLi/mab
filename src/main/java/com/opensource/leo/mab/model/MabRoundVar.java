package com.opensource.leo.mab.model;

import java.io.Serializable;

public class MabRoundVar implements Serializable {
    private double weight;
    private double probability;

    public double calG(double reward, double beta) {
        double g = (reward + beta) / getProbability();
        return g;
    }

    public double calWeight(double g, double eta) {
        double w = getWeight() + eta * g;
        return w;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
