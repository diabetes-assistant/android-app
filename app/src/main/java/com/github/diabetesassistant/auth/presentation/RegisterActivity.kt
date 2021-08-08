package com.github.diabetesassistant.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.diabetesassistant.Dependencies.authService
import com.github.diabetesassistant.R
import com.github.diabetesassistant.auth.domain.Credentials
import com.github.diabetesassistant.auth.domain.User
import com.github.diabetesassistant.core.presentation.RedirectTimings.ONE_SECOND
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
        val snackbarView = binding.registerLayout
        val errorSnackbar = Snackbar.make(
            snackbarView,
            R.string.register_failed,
            Snackbar.LENGTH_LONG
        )
        if (registerViewModel.isInvalid()) {
            errorSnackbar.show()
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                val email = registerViewModel.email.value.toString()
                val password = registerViewModel.password.value.toString()
                val credentials = Credentials(email, password)
                val registerResult = authService.register(credentials)
                registerResult.fold(handleSuccess(snackbarView), handleError(snackbarView))
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

    private fun handleSuccess(view: View): (User) -> Unit {
        return { user: User ->
            Snackbar.make(view, R.string.register_success, Snackbar.LENGTH_LONG).show()
            Log.i("Register", "Successfully registered for user: ${user.email}")
            Thread.sleep(ONE_SECOND)
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun handleError(view: View): (Throwable) -> Unit {
        return { error: Throwable ->
            Snackbar.make(view, R.string.register_failed, Snackbar.LENGTH_LONG).show()
            Log.e("Register", error.stackTraceToString())
        }
    }
}
