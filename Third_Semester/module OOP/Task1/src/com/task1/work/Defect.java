package com.task1.work;

public class Defect {
    private double occurProbability;
    private double identifyProbability;
    private double repairPriceKoef;
    private double repairTimeKoef;

    public Defect(double occurProbability, double findProbability, double repairPriceKoef, double repairTimeKoef) {
        this.occurProbability = occurProbability;
        this.identifyProbability = findProbability;
        this.repairPriceKoef = repairPriceKoef;
        this.repairTimeKoef = repairTimeKoef;
    }

    public double getOccurProbability() {
        return occurProbability;
    }

    public double getIdentifyProbability() {
        return identifyProbability;
    }

    public double getRepairPriceKoef() {
        return repairPriceKoef;
    }

    public double getRepairTimeKoef() {
        return repairTimeKoef;
    }
}
