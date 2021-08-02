package com.github.diabetesassistant.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.diabetesassistant.R
import com.github.diabetesassistant.databinding.ActivityWarningBinding
import com.github.diabetesassistant.dosage.presentation.CalculateDosageActivity

class WarningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWarningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.linearLayout.setBackgroundColor(getColor(R.color.red_warning))
        binding.resumeButton.setOnClickListener(this::handleResume)

        // Herausfinden, ob die Activity wegen zu hohem oder zu niedrigem
        // Blutzuckerspiegel gestartet wurde
        val extras = intent.extras
        val errorType: Int = extras!!.getInt("errorType")
        // Blutzuckerspiegel zu hoch
        if (errorType == 1) {
            binding.warningTextView.setText(getString(R.string.calculate_dosage_glucose_level_too_high))
        } else if (errorType == 2) {
            binding.warningTextView.setText(getString(R.string.calculate_dosage_glucose_level_too_low))
        }
    }

    private fun handleResume(view: View) {
        intent = Intent(applicationContext, CalculateDosageActivity::class.java)
        startActivity(intent)
    }
}
