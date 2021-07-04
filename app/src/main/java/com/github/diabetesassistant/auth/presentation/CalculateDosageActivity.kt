package com.github.diabetesassistant.auth.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.diabetesassistant.databinding.ActivityCalculateDosageBinding

class CalculateDosageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculateDosageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
