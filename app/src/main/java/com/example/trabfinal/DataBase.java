package com.example.trabfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "brlBalance DOUBLE,"+
                "usdBalance DOUBLE,"+
                "bitcoinBalance DOUBLE,"+
                "etherBalance DOUBLE)";
        db.execSQL(CREATE_TABLE);
        ContentValues values = new ContentValues();
        values.put("brlBalance", 1.0);
        values.put("usdBalance", 1.0);
        values.put("bitcoinBalance", 1.0);
        values.put("etherBalance", 1.0);
        db.insert("user", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    //atualiza o saldo do user 1 com o valor passado + o valor atual
    public void insertNewDeposit(String balanceType, User user, double balance)  {
        double actualValue = 0.0;;
        switch (balanceType) {
            case "brlBalance":
                actualValue = user.getBrlBalance();
                break;
            case "usdBalance":
                actualValue = user.getUsdBalance();
                break;
            case "bitcoinBalance":
                actualValue = user.getBitcoinBalance();
                break;
            default:
                actualValue = user.getEtherBalance();
                break;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        double totalValue = actualValue + balance;
        ContentValues values = new ContentValues();
        values.put(balanceType, totalValue);
        db.update("user", values, "id = 1", null);
    }

    //pega o saldo atual do user 1 e retorna
    public void getBalance(User user) {
        double brlBalance = 0.0;
        double usdBalance = 0.0;
        double bitcoinBalance = 0.0;
        double etherBalance = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE id = 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            brlBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("brlBalance"));
            usdBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("usdBalance"));
            bitcoinBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("bitcoinBalance"));
            etherBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("etherBalance"));
        }
        user.setBalance(brlBalance, usdBalance, bitcoinBalance, etherBalance);
        cursor.close();
        db.close();
    }


}
