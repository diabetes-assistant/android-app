package com.github.diabetesassistant.auth.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateDosageViewModel : ViewModel() {
    val glucoseLevel = MutableLiveData("")
    val carbohydrateAmount = MutableLiveData("")
    val insulinDosageRecommended = MutableLiveData("")

    // TODO Hier auch noch klären, ob schon Blutzuckerober- und -untergrenzen berücksichtigt werden sollen
    // TODO Gibt es noch andere Eingaben, die invalid sind?
    fun isInvalid(): Boolean {
        return this.glucoseLevel.value
            .isNullOrBlank() || this.carbohydrateAmount.value
            .isNullOrBlank()
    }
}
