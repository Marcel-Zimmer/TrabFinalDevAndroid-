package com.example.trabfinal

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListViewActivity : AppCompatActivity() {
    private lateinit var user : User
    private lateinit var db : DataBase
    private lateinit var textBrl : TextView
    private lateinit var textUsd : TextView
    private lateinit var textEur : TextView
    private lateinit var textBtc : TextView
    private lateinit var textEth : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DataBase(this)
        user = db.user
        textBrl = findViewById<TextView>(R.id.textViewBrl)
        textUsd = findViewById<TextView>(R.id.textViewUsd)
        textEur = findViewById<TextView>(R.id.textViewEuro)
        textBtc = findViewById<TextView>(R.id.textViewBtc)
        textEth = findViewById<TextView>(R.id.textViewEth)
        textBrl.text = "%.2f".format(user.brlBalance)
        textUsd.text = "%.2f".format(user.usdBalance)
        textEur.text = "%.2f".format(user.eurBalance)
        textBtc.text = user.bitcoinBalance.toString()
        textEth.text = user.etherBalance.toString()


    }



    fun backToMainView(view : View){
        finish()
    }
}