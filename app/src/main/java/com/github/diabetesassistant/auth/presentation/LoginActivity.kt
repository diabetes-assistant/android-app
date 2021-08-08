package com.github.diabetesassistant.auth.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.diabetesassistant.Dependencies.authService
import com.github.diabetesassistant.MainActivity
import com.github.diabetesassistant.R
import com.github.diabetesassistant.auth.domain.Credentials
import com.github.diabetesassistant.auth.domain.Token
import com.github.diabetesassistant.core.presentation.RedirectTimings.ONE_SECOND
import com.github.diabetesassistant.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginEmailAddress.doOnTextChanged(this::setEmailState)
        binding.loginPassword.doOnTextChanged(this::setPasswordState)
        binding.loginSubmitButton.setOnClickListener(this::handleSubmit)
        binding.registerButton.setOnClickListener(this::startRegisterActivity)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleSubmit(view: View) {
        val loginLayout = binding.loginLayout
        val errorSnackbar = Snackbar.make(loginLayout, R.string.login_failed, Snackbar.LENGTH_LONG)
        if (loginViewModel.isInvalid()) {
            errorSnackbar.show()
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                val user = Credentials(
                    loginViewModel.email.value.toString(),
                    loginViewModel.password.value.toString()
                )
                val loginResult: Result<Token> = authService.login(user)
                loginResult.fold(storeToken(loginLayout), handleError(loginLayout))
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun startRegisterActivity(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setEmailState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.loginViewModel.email.value = chars.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setPasswordState(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.loginViewModel.password.value = chars.toString()
    }

    private fun storeToken(view: View): (Token) -> Unit {
        return { token: Token ->
            val appName = getString(R.string.app_prefix)
            val sharedPref = getSharedPreferences(appName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(getString(R.string.access_key), token.accessToken)
                putString(getString(R.string.user_id), token.idToken.subject)
                putString(getString(R.string.email), token.idToken.getClaim("email").asString())
                apply()
            }
            Snackbar.make(view, R.string.login_success, Snackbar.LENGTH_LONG).show()
            Log.i("Login", "Successfully logged in")
            Thread.sleep(ONE_SECOND)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun handleError(view: View): (Throwable) -> Unit {
        return { error: Throwable ->
            Snackbar.make(view, R.string.login_failed, Snackbar.LENGTH_LONG).show()
            Log.e("Login", error.stackTraceToString())
        }
    }
}
