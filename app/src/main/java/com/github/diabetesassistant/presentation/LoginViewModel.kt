package com.github.diabetesassistant.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val text = MutableLiveData<String>().apply {
        value = "This is login Fragment"
    }
}
