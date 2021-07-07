package com.github.diabetesassistant.auth.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordConfirmation = MutableLiveData("")

    fun isInvalid(): Boolean {
        return this.email.value.isNullOrBlank() || this.password.value.isNullOrBlank() ||
            this.passwordConfirmation.value.isNullOrBlank() ||
            this.password.value != this.passwordConfirmation.value
    }
}
