package com.example.trabfinal;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id;
    private double brlBalance;
    private double usdBalance;
    private double bitcoinBalance;
    private double etherBalance;

    // Construtor
    public User() {
        this.id = 1;
        this.brlBalance = 0.0;
        this.usdBalance = 0.0;
        this.bitcoinBalance = 0.0;
        this.etherBalance = 0.0;
    }

    // Método para definir os saldos
    void setBalance(double brlBalance, double usdBalance, double bitcoinBalance, double etherBalance){
        this.brlBalance = brlBalance;
        this.usdBalance = usdBalance;
        this.bitcoinBalance = bitcoinBalance;
        this.etherBalance = etherBalance;
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

    // Construtor necessário para recuperar o objeto de um Parcel
    protected User(Parcel in) {
        id = in.readInt();
        brlBalance = in.readDouble();
        usdBalance = in.readDouble();
        bitcoinBalance = in.readDouble();
        etherBalance = in.readDouble();
    }

    // Escreve os dados no Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(brlBalance);
        dest.writeDouble(usdBalance);
        dest.writeDouble(bitcoinBalance);
        dest.writeDouble(etherBalance);
    }

    // Método necessário para recriar o objeto do Parcel
    @Override
    public int describeContents() {
        return 0;
    }

    // CREATOR: Responsável por criar o objeto a partir do Parcel
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
