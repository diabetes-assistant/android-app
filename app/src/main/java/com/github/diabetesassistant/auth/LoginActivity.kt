package com.github.diabetesassistant.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.R
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.domain.AuthService
import com.github.diabetesassistant.auth.domain.User
import com.github.diabetesassistant.auth.presentation.LoginViewModel
import com.github.diabetesassistant.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var service: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginEmailAddress.doOnTextChanged(this::setEmailState)
        binding.loginPassword.doOnTextChanged(this::setPasswordState)
        binding.loginSubmitButton.setOnClickListener(this::handleSubmit)
        service = AuthService(AuthClient("https://live-diabetes-assistant-be.herokuapp.com/"))
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleSubmit(view: View) {
        val errorSnackbar = Snackbar.make(
            binding.container,
            R.string.login_failed,
            Snackbar.LENGTH_LONG
        )
        if (this.loginViewModel.isInvalid()) {
            errorSnackbar.show()
        }

        val loginResult = service.login(
            User(
                loginViewModel.email.value.toString(),
                loginViewModel.password.value.toString()
            )
        )
        loginResult.fold(
            {
                Snackbar.make(
                    binding.container,
                    R.string.login_success,
                    Snackbar.LENGTH_LONG
                ).show()
                Log.i("Login", "Successfully logged in")
            },
            {
                errorSnackbar.show()
                Log.e("Login", it.stackTraceToString())
            }
        )
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
