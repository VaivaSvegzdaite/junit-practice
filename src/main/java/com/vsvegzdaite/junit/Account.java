package com.vsvegzdaite.junit;

public class Account {
    private double moneyAmount;

    protected Account() {
    }

    protected Account(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
