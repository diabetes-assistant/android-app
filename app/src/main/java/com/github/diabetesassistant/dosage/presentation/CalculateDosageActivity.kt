package com.github.diabetesassistant.dosage.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.databinding.ActivityCalculateDosageBinding
import com.google.android.material.snackbar.Snackbar

class CalculateDosageActivity : AppCompatActivity() {

    private lateinit var calculateDosageViewModel: CalculateDosageViewModel
    private lateinit var binding: ActivityCalculateDosageBinding
    private val TAG ="CalculateDosageActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateDosageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        calculateDosageViewModel = ViewModelProvider(this).get(CalculateDosageViewModel::class.java)
        binding.calculateDosageGlucoseLevel.doOnTextChanged(this::setGlucoseLevelState)
        binding.calculateDosageAmountCarbohydrates
            .doOnTextChanged(this::setAmountCarbohydratesState)
        binding.calculateDosageSubmitButton.setOnClickListener(this::handleSubmit)
        // TODO muss man für calculateDosage das Binding in die andere Richtung, vom ViewModel ausgehend implementieren?
        // TODO bzw. reicht es, wenn das entsprechende Feld im ViewModel über die Methode handleSubmit verändert wird?
        binding.calculateDosageResult.doOnTextChanged(this::setInsulinDosageState)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleSubmit(view: View) {
        val errorSnackbar = Snackbar.make(
            binding.calculateDosageContainer,
            "Calculation failed",
            Snackbar.LENGTH_LONG
        )
        if (calculateDosageViewModel.isInvalid()) {
            errorSnackbar.show()
        } else {
            // TODO hier muss dann die Berechnung der Insulindosis initiiert werden
            // TODO Die Berechnung selber habe ich ins ViewModel verschoben
            calculateDosageViewModel.calculateInsulinDosage()

        }
    }

    // TODO wann immer die Patient:in neue Werte eingibt muss der Wert für die empfohlene Insulindosis zunächst
    // TODO gelöscht werden und darf erst nach Betätigung des Submit-Buttons wieder neu berechnet erscheinen
    @Suppress("UNUSED_PARAMETER")
    private fun setGlucoseLevelState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.calculateDosageViewModel.glucoseLevel.value = chars.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setAmountCarbohydratesState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.calculateDosageViewModel.carbohydrateAmount.value = chars.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setInsulinDosageState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.calculateDosageViewModel.insulinDosageRecommended.value=chars.toString()
    }
}
