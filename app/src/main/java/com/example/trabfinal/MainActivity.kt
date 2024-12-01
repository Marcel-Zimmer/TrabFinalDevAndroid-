package com.example.trabfinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trabfinal.network.ApiModule

class MainActivity : AppCompatActivity() {
    private lateinit var db : DataBase
    private lateinit var textBalance : TextView
    private lateinit var user : User
    private lateinit var api : ApiModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DataBase(this)
        user = db.user
        api = ApiModule()
        updateBalanceBrl()

    }
    override fun onResume() {
        super.onResume();
        updateBalanceBrl();
    }



    fun callDepositView(view : View){
        val intent = Intent(this, DepositActivity::class.java)
        startActivity(intent)
    }
    fun callTradeView(view : View){
        val intent = Intent(this, ConverterActivity::class.java)
        startActivity(intent)
    }
    fun callListView(){
        val intent = Intent(this, ListViewActivity::class.java)
        startActivity(intent)
    }


    private fun updateBalanceBrl(){
        db.getBalance()
        textBalance = findViewById(R.id.textViewBalance)
        textBalance.text = "Saldo R$ ${user.brlBalance}"
    }


}