package com.example.trabfinal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trabfinal.network.ApiModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConverterActivity : AppCompatActivity() {
    lateinit var coinOriginSelected :String
    lateinit var coinDestinationSelected :String
    lateinit var currencyAmount  : EditText
    lateinit var api : ApiModule
    lateinit var spinnerCoinOrigin: Spinner
    lateinit var spinnerCoinDestination: Spinner
    lateinit var textResult : TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_converter)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
        currencyAmount = findViewById<EditText>(R.id.editTextNumberDecimal2)
        textResult = findViewById<TextView>(R.id.textView8)
        val amount = currencyAmount.getText().toString().toDouble()
        api = ApiModule();
        CoroutineScope(Dispatchers.Main).launch {
            val exchangeRate: Double= api.getCurrentExchangeRate(coinOriginSelected, coinDestinationSelected)
            textResult.text = (exchangeRate * amount).toString()

        }

    }
}