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
                "balance DOUBLE)";
        db.execSQL(CREATE_TABLE);
        ContentValues values = new ContentValues();
        values.put("balance", 1.0);
        db.insert("user", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    //atualiza o saldo do user 1 com o valor passado + o valor atual
    public void insertNewDeposit(double balance)  {
        double totalValue = getBalance();
        SQLiteDatabase db = this.getWritableDatabase();
        totalValue = totalValue + balance;
        ContentValues values = new ContentValues();
        values.put("balance", totalValue);
        db.update("user", values, "id = 1", null);
    }

    //pega o saldo atual do user 1 e retorna
    public double getBalance() {
        double balance = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT balance FROM user WHERE id = 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            balance = cursor.getDouble(cursor.getColumnIndexOrThrow("balance"));
        }
        cursor.close();
        db.close();
        return balance;
    }


}
