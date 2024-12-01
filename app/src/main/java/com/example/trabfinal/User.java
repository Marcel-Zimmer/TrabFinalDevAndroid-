package com.example.trabfinal;


public class User {
    private int id;
    private double brlBalance;
    private double usdBalance;
    private double eurBalance;
    private double bitcoinBalance;
    private double etherBalance;

    // Construtor
    public User() {
        this.id = 1;
        this.brlBalance = 0.0;
        this.usdBalance = 0.0;
        this.bitcoinBalance = 0.0;
        this.etherBalance = 0.0;
        this.eurBalance = 0.0;
    }

    // Getters
    public double getBrlBalance() {
        return brlBalance;
    }

    public double getUsdBalance() {
        return usdBalance;
    }

    public double getBitcoinBalance() {
        return bitcoinBalance;
    }

    public double getEtherBalance() {
        return etherBalance;
    }

    public double getEurBalance() {
        return eurBalance;
    }

    //Setters
    public void setUsdBalance(double usdBalance) {
        this.usdBalance = usdBalance;
    }

    public void setBitcoinBalance(double bitcoinBalance) {
        this.bitcoinBalance = bitcoinBalance;
    }
    public void setEtherBalance(double etherBalance) {
        this.etherBalance = etherBalance;
    }
    public void setEurBalance(double eurBalance) {
        this.eurBalance = eurBalance;
    }

    public void setBrlBalance(double brlBalance) {
        this.brlBalance = brlBalance;
    }
}
