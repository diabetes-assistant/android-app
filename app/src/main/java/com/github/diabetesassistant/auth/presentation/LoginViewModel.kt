package com.github.diabetesassistant.auth.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun isInvalid(): Boolean {
        return this.email.value.isNullOrBlank() || this.password.value.isNullOrBlank()
    }
}
