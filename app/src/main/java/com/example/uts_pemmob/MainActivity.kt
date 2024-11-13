package com.example.uts_pemmob

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fungsi enableEdgeToEdge() dihapus karena tidak ada di API standar.
    }

    // Metode onClick untuk setiap menu
    fun launchTemperatureConverter(view: android.view.View) {
        val intent = Intent(this, TemperatureActivity::class.java)
        startActivity(intent)
    }

    fun launchCalculator(view: android.view.View) {
        val intent = Intent(this, CalculatorActivity::class.java)
        startActivity(intent)
    }

    fun launchBMICalculator(view: android.view.View) {
        val intent = Intent(this, BMIActivity::class.java)
        startActivity(intent)
    }

    fun launchCurrencyConverter(view: android.view.View) {
        val intent = Intent(this, MoneyConvertActivity::class.java)
        startActivity(intent)
    }
}
