package com.github.diabetesassistant.auth.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.diabetesassistant.databinding.ActivityCalculateDosageBinding
import com.github.diabetesassistant.databinding.ActivityWarningBinding
import com.github.diabetesassistant.dosage.presentation.CalculateDosageViewModel

class WarningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWarningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWarningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}