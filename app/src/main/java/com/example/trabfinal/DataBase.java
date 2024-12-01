package com.example.trabfinal;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLException;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    private final User user ;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.user = new User();
        getBalance();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE user (" +
                "id INTEGER PRIMARY KEY, " +
                "brlBalance DOUBLE,"+
                "usdBalance DOUBLE,"+
                "eurBalance DOUBLE,"+
                "bitcoinBalance DOUBLE,"+
                "etherBalance DOUBLE)";
        db.execSQL(CREATE_TABLE);
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("brlBalance", 1.0);
        values.put("usdBalance", 1.0);
        values.put("eurBalance", 1.0);
        values.put("bitcoinBalance", 1.0);
        values.put("etherBalance", 1.0);
        db.insert("user", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
    public User getUser(){
        return user;
    }

    //atualiza o saldo do user 1 com o valor passado + o valor atual
    public void insertNewDeposit(String balanceType, double balance) {
        double actualValue = 0.0;
        switch (balanceType) {
            case "brlBalance":
                actualValue = user.getBrlBalance();
                break;
            case "usdBalance":
                actualValue = user.getUsdBalance();
                break;
            case "eurBalance":
                actualValue = user.getEurBalance();
                break;
            case "bitcoinBalance":
                actualValue = user.getBitcoinBalance();
                break;
            case "etherBalance":
                actualValue = user.getEtherBalance();
                break;
            default:
                throw new IllegalArgumentException("Tipo de saldo inválido: " + balanceType);
        }

        double totalValue = actualValue + balance;

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(balanceType, totalValue);
            int rowsAffected = db.update("user", values, "id = ?", new String[]{"1"});
            if (rowsAffected == 0) {
                throw new SQLException("Falha ao atualizar o saldo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //pega o saldo atual do user 1 e retorna
    public void getBalance() {
        double brlBalance = 0.0;
        double usdBalance = 0.0;
        double eurBalance = 0.0;
        double bitcoinBalance = 0.0;
        double etherBalance = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE id = 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            brlBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("brlBalance"));
            usdBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("usdBalance"));
            eurBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("eurBalance"));
            bitcoinBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("bitcoinBalance"));
            etherBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("etherBalance"));
        }
        user.setBrlBalance(brlBalance);
        user.setUsdBalance(usdBalance);
        user.setEurBalance(eurBalance);
        user.setBitcoinBalance(bitcoinBalance);
        user.setEtherBalance(etherBalance);
        cursor.close();
        db.close();
    }


}
