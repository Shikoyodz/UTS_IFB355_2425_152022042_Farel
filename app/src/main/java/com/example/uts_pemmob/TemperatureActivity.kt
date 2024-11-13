package com.example.uts_pemmob

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.uts_pemmob.databinding.ActivityTemperatureBinding
import java.text.DecimalFormat

class TemperatureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTemperatureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemperatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))

        // Setup Spinner with temperature units
        val temperatureUnits = arrayOf("Celsius", "Kelvin", "Fahrenheit")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, temperatureUnits)
        binding.spinnerFrom.adapter = arrayAdapter
        binding.spinnerTo.adapter = arrayAdapter

        // Set listener for the convert button
        binding.convertButton.setOnClickListener { convertTemperature() }
    }

    private fun convertTemperature() {
        val inputTemperatureText = binding.temperatureInput.text.toString()
        if (inputTemperatureText.isBlank()) {
            "Please enter a valid numeric value".showToast()
            return
        }

        val inputTemperature = inputTemperatureText.toDoubleOrNull()
        if (inputTemperature == null) {
            "Please enter a valid numeric value".showToast()
            return
        }

        val fromUnitPosition = binding.spinnerFrom.selectedItemPosition
        val toUnitPosition = binding.spinnerTo.selectedItemPosition

        val conversionResult = calculateConversion(inputTemperature, fromUnitPosition, toUnitPosition)
        displayResult(conversionResult)
    }

    private fun calculateConversion(value: Double, fromUnit: Int, toUnit: Int): Double {
        return when (fromUnit) {
            0 -> calculateCelsiusToOther(value, toUnit) // Celsius
            1 -> calculateKelvinToOther(value, toUnit) // Kelvin
            2 -> calculateFahrenheitToOther(value, toUnit) // Fahrenheit
            else -> 0.0
        }
    }

    private fun calculateCelsiusToOther(celsius: Double, toUnit: Int): Double {
        return when (toUnit) {
            0 -> celsius // Celsius
            1 -> celsius + 273.15 // Kelvin
            2 -> (celsius * 9 / 5) + 32 // Fahrenheit
            else -> 0.0
        }
    }

    private fun calculateKelvinToOther(kelvin: Double, toUnit: Int): Double {
        return when (toUnit) {
            0 -> kelvin - 273.15 // Celsius
            1 -> kelvin // Kelvin
            2 -> (kelvin - 273.15) * 9 / 5 + 32 // Fahrenheit
            else -> 0.0
        }
    }

    private fun calculateFahrenheitToOther(fahrenheit: Double, toUnit: Int): Double {
        return when (toUnit) {
            0 -> (fahrenheit - 32) * 5 / 9 // Celsius
            1 -> ((fahrenheit - 32) * 5 / 9) + 273.15 // Kelvin
            2 -> fahrenheit // Fahrenheit
            else -> 0.0
        }
    }

    private fun displayResult(result: Double) {
        val resultText = DecimalFormat("#.#####").format(result)
        binding.conversionResult.text = resultText
        binding.conversionResult.visibility = View.VISIBLE
    }

    private fun String.showToast() {
        Toast.makeText(this@TemperatureActivity, this, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Close the activity and go back to the previous one
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}