package com.github.diabetesassistant.dosage.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.databinding.ActivityShowCalculationResultBinding

class ShowCalculationResult : AppCompatActivity() {

    private lateinit var showCalculationResultViewModel: ShowCalculationResultViewModel
    private lateinit var binding: ActivityShowCalculationResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowCalculationResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showCalculationResultViewModel=ViewModelProvider(this).get(ShowCalculationResultViewModel::class.java)


    }
}