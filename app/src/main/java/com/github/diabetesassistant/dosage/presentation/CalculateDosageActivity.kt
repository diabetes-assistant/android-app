package com.github.diabetesassistant.dosage.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.R
import com.github.diabetesassistant.databinding.ActivityCalculateDosageBinding
import com.google.android.material.snackbar.Snackbar

class CalculateDosageActivity : AppCompatActivity() {

    private lateinit var calculateDosageViewModel: CalculateDosageViewModel
    private lateinit var binding: ActivityCalculateDosageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateDosageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        calculateDosageViewModel = ViewModelProvider(this).get(CalculateDosageViewModel::class.java)
        binding.calculateDosageGlucoseLevel
            .doOnTextChanged(this::setGlucoseLevelState)
        binding.calculateDosageAmountCarbohydrates
            .doOnTextChanged(this::setAmountCarbohydratesState)
        binding.calculateDosageSubmitButton.setOnClickListener(this::handleSubmit)
        binding.calculateDosageClear.setOnClickListener(this::handleClear)
        binding.calculateDosageSave.setOnClickListener(this::handleSave)
        binding.calculateDosageResultDescription.setText("")
    }

    // TODO wann immer die Patient:in neue Werte eingibt muss der Wert für die empfohlene Insulindosis zunächst
    // TODO gelöscht werden und darf erst nach Betätigung des Submit-Buttons wieder neu berechnet erscheinen
    @Suppress("UNUSED_PARAMETER")
    private fun handleSubmit(view: View) {
        val errorSnackbar = Snackbar.make(
            binding.calculateDosageContainer,
            "",
            Snackbar.LENGTH_LONG
        )
        // Um den snackbar oben in der Activity darzustellen:
        // https://stackoverflow.com/questions/31746300/how-to-show-snackbar-at-top-of-the-screen
        val params = errorSnackbar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        errorSnackbar.view.layoutParams = params

        if (calculateDosageViewModel.isInvalid()) {
            // https://stackoverflow.com/questions/44871481/how-to-access-values-from-strings-xml
            errorSnackbar.setText(getString(R.string.calculate_dosage_invalid_input))
            errorSnackbar.show()
            return
        } else if (calculateDosageViewModel.isGlucoseLevelTooHigh()) {
            // Am besten ist es, bei zu hohen oder zu niedrigen Blutzuckerwerten
            // nicht eine snackbar einzublenden, sondern eine neue Activity aufzurufen,
            // eine snackbar wäre zu wenig eindrucksvoll
            var intent: Intent? = null
            intent = Intent(applicationContext, WarningActivity::class.java)
            intent.putExtra("errorType", 1)
            startActivity(intent)
        } else if (calculateDosageViewModel.isGlucoseLevelTooLow()) {
            var intent: Intent? = null
            intent = Intent(applicationContext, WarningActivity::class.java)
            intent.putExtra("errorType", 2)
            startActivity(intent)
        } else {
            // Hier wird die Berechnung der Insulindosis initiiert,
            // die Berechnung selber findet im ViewModel statt
            calculateDosageViewModel.calculateInsulinDosage()
            if (calculateDosageViewModel.insulinDosageRecommended.value.toString().toInt() > 0) {
                // Wenn die Berechnung richtig verlaufen ist, dann
                // könnte man in die ShowCalculationResultActivity wechseln
                // startActivity(Intent(this, ShowCalculationResult::class.java))

                // Alternativ: Alles weitere in dieser Activity veranlassen
                binding.calculateDosageResult
                    .setText(
                        this.calculateDosageViewModel.insulinDosageRecommended.value + " IE"
                    )
                binding.calculateDosageResultDescription.setText(
                    getString(
                        R.string.calculate_dosage_result_description
                    )
                )
                val viewCalculateDosageClear: View = binding.calculateDosageClear
                viewCalculateDosageClear.visibility = View.VISIBLE
                val viewCalculateDosageSave: View = binding.calculateDosageSave
                viewCalculateDosageSave.visibility = View.VISIBLE
            } else {
                errorSnackbar.setText(getString(R.string.calculate_dosage_no_insulin_recommended))
                errorSnackbar.show()
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleClear(view: View) {
        this.calculateDosageViewModel.clearViewModel()
        binding.calculateDosageGlucoseLevel.setText(
            calculateDosageViewModel.glucoseLevel.value.toString()
        )
        binding.calculateDosageAmountCarbohydrates.setText(
            calculateDosageViewModel.carbohydrateAmount.value.toString()
        )
        binding.calculateDosageResult.setText(
            calculateDosageViewModel.insulinDosageRecommended.value.toString()
        )
        val viewCalculateDosageClear: View = binding.calculateDosageClear
        viewCalculateDosageClear.visibility = View.INVISIBLE
        val viewCalculateDosageSave: View = binding.calculateDosageSave
        viewCalculateDosageSave.visibility = View.INVISIBLE
    }

    /**
     * TODO !! Hier weitermachen, die eingegebenen/ errechneten DBEntries jeweils an das entsprechende
     * TODO ArrayList anfügen und diese dann in der ShowDiaryActivity sichtbar machen.
     */
    @Suppress("UNUSED_PARAMETER")
    private fun handleSave(view: View) {
        /* Deaktiviert wegen static_code_check-Warnung
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        var glucoseLevelDBEntry: GlucoseLevelDBEntry = GlucoseLevelDBEntry(
            0, currentDateTime,
            this.calculateDosageViewModel.glucoseLevel.value.toString().toInt()
        )
        var insulinDosageDBEntry: InsulinDosageDBEntry = InsulinDosageDBEntry(
            0, currentDateTime,
            this.calculateDosageViewModel.insulinDosageRecommended.value.toString().toInt()
        )
        */
        this.calculateDosageViewModel.clearViewModel()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setGlucoseLevelState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.calculateDosageViewModel.glucoseLevel.value = chars.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setAmountCarbohydratesState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.calculateDosageViewModel.carbohydrateAmount.value = chars.toString()
    }
}
