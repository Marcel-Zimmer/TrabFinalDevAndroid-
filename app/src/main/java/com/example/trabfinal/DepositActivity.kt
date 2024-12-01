package com.example.trabfinal

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DepositActivity() : AppCompatActivity() {
    private lateinit var inputDepostit: String
    private lateinit var db: DataBase
    private lateinit var user: User

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
        user = db.user
    }

    fun depositValue(view : View){
        inputDepostit = findViewById<EditText>(R.id.editInputValue).text.toString()

        if(inputDepostit.isEmpty() || inputDepostit.toDouble() <= 0){
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show()
        }
        else{
            db.insertNewDeposit("brlBalance",inputDepostit.toDouble())
            Toast.makeText(this, "Depósito realizado com sucesso", Toast.LENGTH_SHORT).show()
        }

    }
    fun callMainView(view : View){
        finish()
    }

}
