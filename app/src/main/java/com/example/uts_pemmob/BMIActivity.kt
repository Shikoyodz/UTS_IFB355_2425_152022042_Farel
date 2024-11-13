package com.example.uts_pemmob

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat


class BMIActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var textViewResult: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        // Initialize views
        editTextName = findViewById(R.id.editTextName)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextWeight = findViewById(R.id.editTextWeight)
        editTextHeight = findViewById(R.id.editTextHeight)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        textViewResult = findViewById(R.id.textViewResult)

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

        // Calculate button click listener
        findViewById<Button>(R.id.buttonCalculate).setOnClickListener {
            calculateBMI()
        }

        // Reset button click listener
        findViewById<Button>(R.id.buttonReset).setOnClickListener {
            resetFields()
        }
    }

    private fun calculateBMI() {
        val name = editTextName.text.toString()
        val address = editTextAddress.text.toString()
        val phone = editTextPhone.text.toString()
        val weight = editTextWeight.text.toString().toDoubleOrNull()
        val height = editTextHeight.text.toString().toDoubleOrNull()

        if (weight == null || height == null || name.isBlank() || address.isBlank() || phone.isBlank()) {
            textViewResult.text = "Please fill all fields correctly."
            return
        }

        val gender = if (radioGroupGender.checkedRadioButtonId == R.id.radioMale) "Male" else "Female"
        val bmi = weight / (height * height)
        val result = "Name: $name\nAddress: $address\nPhone: $phone\nGender: $gender\nBMI: %.2f".format(bmi)

        textViewResult.text = result
    }

    private fun resetFields() {
        editTextName.text.clear()
        editTextAddress.text.clear()
        editTextPhone.text.clear()
        editTextWeight.text.clear()
        editTextHeight.text.clear()
        radioGroupGender.clearCheck()
        textViewResult.text = ""
    }

    // Override onOptionsItemSelected to handle the Back button in the Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Close the activity and go back to the previous one
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
