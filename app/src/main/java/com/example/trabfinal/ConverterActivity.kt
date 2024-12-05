package com.example.trabfinal

import android.graphics.pdf.content.PdfPageGotoLinkContent.Destination
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trabfinal.network.ApiModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConverterActivity : AppCompatActivity() {
    private lateinit var coinOriginSelected :String
    private lateinit var coinDestinationSelected :String
    private lateinit var currencyAmount  : EditText
    private lateinit var api : ApiModule
    private lateinit var spinnerCoinOrigin: Spinner
    lateinit var spinnerCoinDestination: Spinner
    private lateinit var textConverter : TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var user : User
    private lateinit var db : DataBase




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_converter)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textConverter = findViewById(R.id.textView3);
        progressBar = findViewById(R.id.progressBar);
        currencyAmount = findViewById(R.id.editTextNumberDecimal2)
        textConverter.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE
        db = DataBase(this)
        user = db.user


        val listCoins = listOf("Selecione uma moeda","BRL", "USD", "EUR", "ETH", "BTC");
        spinnerCoinOrigin= findViewById(R.id.spinner2)
        spinnerCoinDestination= findViewById(R.id.spinner3)

        spinnerCoinOrigin.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listCoins
        )
        val listDefout = listOf("Selecione a moeda de origem")
        spinnerCoinDestination.adapter = ArrayAdapter(
            this@ConverterActivity,
            android.R.layout.simple_spinner_item,
            listDefout
        )


        spinnerCoinOrigin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position != 0){
                    val listCoinsResponsive =
                        listCoins.toMutableList().apply { removeAt(position) }.toTypedArray();

                    responsiveCoinList(listCoinsResponsive)
                }

            }

            fun responsiveCoinList(listCoinsResponsive: Array<String>) {
                spinnerCoinDestination.adapter = ArrayAdapter(
                    this@ConverterActivity,
                    android.R.layout.simple_spinner_item,
                    listCoinsResponsive
                )
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {

            }




    }


    }
    fun closeView(view: View) {
        finish()

    }

    fun tradeCoins(view: View) {
        coinOriginSelected = spinnerCoinOrigin.selectedItem.toString()
        coinDestinationSelected = spinnerCoinDestination.selectedItem.toString()
        val amount = currencyAmount.getText().toString().toDouble()

        if(testBalance(coinOriginSelected, amount)){
            textConverter.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            api = ApiModule();
            CoroutineScope(Dispatchers.Main).launch {
                val exchangeRate: Double= api.getCurrentExchangeRate(coinOriginSelected, coinDestinationSelected)
                setBalance(coinOriginSelected, coinDestinationSelected, exchangeRate * amount, amount)
                db.updateBalance()
                progressBar.visibility = View.INVISIBLE
                textConverter.text = "o valor convertido foi de :" + exchangeRate * amount + coinDestinationSelected ;
                currencyAmount.setText("")

            }

        }
        else{
            Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
        }

    }

    fun testBalance(coin : String, value: Double):Boolean{
        var balance = 0.0
        balance = if(coin.equals("BRL")){
            user.brlBalance
        } else if(coin.equals("USD")){
            user.usdBalance
        } else if(coin.equals("EUR")){
            user.eurBalance
        } else if(coin.equals("BTC")){
            user.bitcoinBalance
        } else{
            user.etherBalance
        }
        return if(value <= balance && balance>0){
            true
        } else{
            false
        }
    }
    fun setBalance(coinOrigem : String, coinDestination: String, value : Double, amout:Double){
        if(coinOrigem.equals("BRL")){
            user.setBrlBalance(user.brlBalance - amout)
        }
        else if(coinOrigem.equals("USD")){
            user.setUsdBalance(user.usdBalance - amout)
        }
        else if(coinOrigem.equals("EUR")){
            user.setEurBalance(user.eurBalance - amout)
        }
        else if(coinOrigem.equals("BTC")){
            user.setBitcoinBalance(user.bitcoinBalance - amout)
        }else{
            user.setEtherBalance(user.etherBalance - amout)
        }

        if(coinDestination.equals("BRL")){
            user.setBrlBalance(user.brlBalance + value)
        }
        else if(coinDestination.equals("USD")){
            user.setUsdBalance(user.usdBalance + value)
        }
        else if(coinDestination.equals("EUR")){
            user.setEurBalance(user.eurBalance + value)
        }
        else if(coinDestination.equals("BTC")){
            user.setBitcoinBalance(user.bitcoinBalance + value)
        }else{
            user.setEtherBalance(user.etherBalance + value)
        }
    }
}