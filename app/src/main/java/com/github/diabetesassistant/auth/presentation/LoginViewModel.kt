package com.github.diabetesassistant.auth.presentation

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.diabetesassistant.R
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.domain.AuthService
import com.github.diabetesassistant.auth.domain.Token
import com.github.diabetesassistant.auth.domain.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    private val baseUrl = "https://live-diabetes-assistant-be.herokuapp.com/"
    private val service = AuthService(AuthClient(baseUrl))

    private fun isInvalid(): Boolean {
        return this.email.value.isNullOrBlank() || this.password.value.isNullOrBlank()
    }

    fun login(view: View) {
        val errorSnackbar = Snackbar.make(view, R.string.login_failed, Snackbar.LENGTH_LONG)
        if (this.isInvalid()) {
            errorSnackbar.show()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val user = User(email.value.toString(), password.value.toString())
                val loginResult: Result<Token> = service.login(user)
                loginResult.fold(storeToken(view), handleError(view))
            }
        }
    }

    private fun storeToken(view: View): (Token) -> Unit {
        return { _: Token ->
            Snackbar.make(view, R.string.login_success, Snackbar.LENGTH_LONG).show()
            Log.i("Login", "Successfully logged in")
        }
    }

    private fun handleError(view: View): (Throwable) -> Unit {
        return { error: Throwable ->
            Snackbar.make(view, R.string.login_failed, Snackbar.LENGTH_LONG).show()
            Log.e("Login", error.stackTraceToString())
        }
    }
}
