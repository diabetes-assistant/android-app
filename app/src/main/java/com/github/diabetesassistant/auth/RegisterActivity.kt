package com.github.diabetesassistant.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.auth.presentation.RegisterViewModel
import com.github.diabetesassistant.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.registerEmailAddress.doOnTextChanged(this::setEmailState)
        binding.registerPassword.doOnTextChanged(this::setPasswordState)
        binding.registerPasswordConfirmation.doOnTextChanged(this::setPasswordConfirmationState)
        binding.registerSubmitButton.setOnClickListener(this::handleSubmit)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setEmailState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.registerViewModel.email.value = chars.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setPasswordState(chars: CharSequence?, a: Int, b: Int, c: Int) {
            this.registerViewModel.password.value = chars.toString()

    }

    @Suppress("UNUSED_PARAMETER")
    private fun setPasswordConfirmationState(chars: CharSequence?,i: Int, i1: Int, i2: Int) {
        this.registerViewModel.passwordConfirmation.value = chars.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleSubmit(view: View) {
        registerViewModel.register(binding.registerContainer)
    }
}
