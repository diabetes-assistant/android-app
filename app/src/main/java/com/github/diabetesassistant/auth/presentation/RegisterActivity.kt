package com.github.diabetesassistant.auth.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.diabetesassistant.R
import com.github.diabetesassistant.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.registerEmailAddress.doOnTextChanged(this::setEmailState)
        binding.registerPassword.doOnTextChanged(this::setPasswordState)
        binding.registerPasswordConfirmation.doOnTextChanged(this::setPasswordConfirmationState)
        binding.registerSubmitButton.setOnClickListener(this::handleSubmit)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleSubmit(view: View) {
        val errorSnackbar = Snackbar.make(
            binding.registerContainer,
            R.string.register_failed,
            Snackbar.LENGTH_LONG
        )
        if (registerViewModel.isInvalid()) {
            errorSnackbar.show()
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                // TODO Hier muss die Verbindung zum Registrier-Vorgang im Backend implementiert werden
                /*
                val loginResult: Result<Token> = authService.register(user)
                loginResult.fold(storeToken(binding.registerContainer), handleError(binding.registerContainer))
                */
            }
        }
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
    private fun setPasswordConfirmationState(chars: CharSequence?, i: Int, i1: Int, i2: Int) {
        this.registerViewModel.passwordConfirmation.value = chars.toString()
    }

    // Aus LoginActivity.kt übernommen
    /*
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
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    */

    // Aus LoginActivity.kt übernommen
    /*
    private fun handleError(view: View): (Throwable) -> Unit {
        return { error: Throwable ->
            Snackbar.make(view, R.string.login_failed, Snackbar.LENGTH_LONG).show()
            Log.e("Login", error.stackTraceToString())
        }
    }
    */
}
