package com.example.trabfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trabfinal.network.ApiModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        user = User()
        db = DataBase(this)
        api = ApiModule()
        updateBalanceBrl()

    }
    override fun onResume() {
        super.onResume();
        updateBalanceBrl();
    }



    fun callDepositView(view : View){
        val intent = Intent(this, DepostitActivity::class.java)
        intent.putExtra("user", user);
        startActivity(intent)
    }
    fun callTradeView(view : View){
        val intent = Intent(this, ConverterActivity::class.java)
        //intent.putExtra("user", user);
        startActivity(intent)
    }


    fun updateBalanceBrl(){
        db.getBalance(user)
        textBalance = findViewById(R.id.textViewBalance)
        textBalance.text = user.brlBalance.toString()
    }


}