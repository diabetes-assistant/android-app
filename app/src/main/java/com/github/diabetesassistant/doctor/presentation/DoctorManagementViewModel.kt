package com.github.diabetesassistant.doctor.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DoctorManagementViewModel : ViewModel() {
    val confirmationCode = MutableLiveData("")
    private val allowedCharacters = Regex("[a-zA-Z0-9]{6}")

    fun isValid(confirmationCode: String): Boolean {
        return confirmationCode.matches(allowedCharacters)
    }
}
