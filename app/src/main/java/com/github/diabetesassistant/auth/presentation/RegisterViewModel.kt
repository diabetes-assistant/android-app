package com.github.diabetesassistant.auth.presentation

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.diabetesassistant.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
