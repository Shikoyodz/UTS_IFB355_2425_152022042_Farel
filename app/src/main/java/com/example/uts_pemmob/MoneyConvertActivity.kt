package com.example.uts_pemmob

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat

class MoneyConvertActivity : AppCompatActivity() {

    private lateinit var amountInput: EditText
    private lateinit var currencyFromSpinner: Spinner
    private lateinit var currencyToSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var swapButton: ImageButton

    // Currency rates for demonstration purposes
    private val currencyRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "GBP" to 0.75,
        "JPY" to 110.0,
        "AUD" to 1.35,
        "CAD" to 1.25,
        "IDR" to 14200.0
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moneyconvert)

        // Initialize views
        amountInput = findViewById(R.id.amountInput)
        currencyFromSpinner = findViewById(R.id.currencyFromSpinner)
        currencyToSpinner = findViewById(R.id.currencyToSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)
        swapButton = findViewById(R.id.swapButton)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawable = toolbar.navigationIcon
        drawable?.let {
            DrawableCompat.setTint(it, resources.getColor(android.R.color.white, theme))
            toolbar.navigationIcon = it
        }
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))

        // Setup spinners with currency options
        setupCurrencySpinners()

        // Set click listeners for buttons
        convertButton.setOnClickListener { convertCurrency() }
        swapButton.setOnClickListener { swapCurrencies() }
    }

    private fun setupCurrencySpinners() {
        val currencies = currencyRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        currencyFromSpinner.adapter = adapter
        currencyToSpinner.adapter = adapter
    }

    private fun swapCurrencies() {
        val fromCurrencyPosition = currencyFromSpinner.selectedItemPosition
        val toCurrencyPosition = currencyToSpinner.selectedItemPosition

        // Tukar posisi spinner
        currencyFromSpinner.setSelection(toCurrencyPosition)
        currencyToSpinner.setSelection(fromCurrencyPosition)

        // Ambil hasil konversi terakhir dan set sebagai input baru
        val lastResultText = resultTextView.text.toString()
        if (lastResultText.isNotBlank()) {
            amountInput.setText(lastResultText)
        }

        // Kosongkan hasil konversi sementara untuk menunjukkan sedang dihitung ulang
        resultTextView.text = ""

        Toast.makeText(this, "Currencies swapped!", Toast.LENGTH_SHORT).show()

        // Panggil fungsi convertCurrency untuk menghitung ulang nilai setelah swap
        convertCurrency()
    }

    private fun convertCurrency() {
        var amountText = amountInput.text.toString()

        // Ganti koma dengan titik untuk parsing angka
        amountText = amountText.replace(",", ".")

        if (amountText.isBlank()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            return
        }

        val fromCurrency = currencyFromSpinner.selectedItem.toString()
        val toCurrency = currencyToSpinner.selectedItem.toString()

        val convertedAmount = convert(amount, fromCurrency, toCurrency)
        resultTextView.text = String.format("%.2f", convertedAmount)
    }

    private fun convert(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val fromRate = currencyRates[fromCurrency] ?: 1.0
        val toRate = currencyRates[toCurrency] ?: 1.0
        return amount / fromRate * toRate
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Close the activity and return to the previous screen
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
