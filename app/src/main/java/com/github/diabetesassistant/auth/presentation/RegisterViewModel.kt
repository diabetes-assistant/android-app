package com.github.diabetesassistant.auth.presentation

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.diabetesassistant.R
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.domain.AuthService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordConfirmation = MutableLiveData("")
    private val baseUrl = "https://live-diabetes-assistant-be.herokuapp.com/"
    private val service = AuthService(AuthClient(baseUrl))

    private fun isInvalid(): Boolean {
        return this.email.value.isNullOrBlank() || this.password.value.isNullOrBlank() ||
                this.passwordConfirmation.value.isNullOrBlank() ||
                this.password.value != this.passwordConfirmation.value
    }

    fun register(view: View) {
        val errorSnackbar = Snackbar.make(view, R.string.register_failed, Snackbar.LENGTH_LONG)
        if (this.isInvalid()) {
            errorSnackbar.show()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val registerSnackbar =
                    Snackbar.make(view, R.string.register_success, Snackbar.LENGTH_LONG)
                registerSnackbar.show()
            }
        }
    }
}
