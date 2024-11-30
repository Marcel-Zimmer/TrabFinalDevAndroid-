package com.example.trabfinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Depostit : AppCompatActivity() {
    private lateinit var inputDepostit: String
    private lateinit var db: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_deposti)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DataBase(this)
    }

    fun depositValue(view : View){
        inputDepostit = findViewById<EditText>(R.id.editInputValue).text.toString()

        if(inputDepostit.isEmpty() || inputDepostit.toDouble() <= 0){
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show()
        }
        else{
            db.insertNewDeposit(inputDepostit.toDouble())
            Toast.makeText(this, "Depósito realizado com sucesso", Toast.LENGTH_SHORT).show()
        }

    }
    fun callMainView(view : View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
