package com.github.diabetesassistant.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.R
import com.github.diabetesassistant.auth.presentation.LoginViewModel
import com.github.diabetesassistant.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginEmailAddress.doOnTextChanged(this::setEmailState)
        binding.loginPassword.doOnTextChanged(this::setPasswordState)
        binding.loginSubmitButton.setOnClickListener(this::handleSubmit)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleSubmit(view: View) {
        if (this.loginViewModel.isInvalid()) {
            Snackbar.make(binding.container, R.string.login_failed, Snackbar.LENGTH_LONG).show()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setEmailState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.loginViewModel.email.value = chars.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setPasswordState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.loginViewModel.password.value = chars.toString()
    }
}