package com.github.diabetesassistant.dosage.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class CalculateDosageViewModel : ViewModel() {
    val tag: String = "CalculateDosageViewModel"

    // Felder, die auch im UI sichtbar sind
    // TODO wieso werden hier val (final) und nicht var verwendet?
    val glucoseLevel = MutableLiveData("")
    val carbohydrateAmount = MutableLiveData("")
    val insulinDosageRecommended = MutableLiveData("")

    // Durch die Ärzt:in voreingestellte Felder
    // Kohlenhydrateinheit
    // The amount of insulin [IE] which is needed to normalize the blood glucose level
    // per intake of 1 carbohydrate unit (10 g of carbohydrates)
    // Will finally be entered by the doctor but is arbitrarily set here to 0.5 for testing purposes
    // TODO sollte durch Arzt gesetzt werden
    val keFactor: Float = 0.5F

    // Größe eines Korrekturintervalls in [mg/dl]
    // TODO sollte durch Ärzt:in gesetzt werden, hier aber zu Testzwecken erstmal auf 20 mg/dl gesetzt
    val glucoseLevelCorrectionInterval: Int = 20

    // Korrektureinheiten: Insulineinheiten [IE], die pro Korrekturschritt erforderlich sind
    // TODO sollte durch Ärzt:in gesetzt werden, hier aber erstmal auf 2 IE gesetzt
    val glucoseLevelCorrectionDosage: Int = 2

    // Oberer Normwert für den Blutzuckerspiegel, i.d.R. 120 mg/dl
    // TODO dieser obere Blutzuckernormwert sollte durch Ärzt:in änderbar sein, hier aber erstmal auf 120 mg/dl gesetzt
    val glucoseLevelUpperLimit: Int = 120

    // TODO Hier noch klären, ob schon Blutzuckerober- und -untergrenzen berücksichtigt werden sollen
    // TODO Gibt es noch andere Eingaben, die invalid sind?
    fun isInvalid(): Boolean {
        return this.glucoseLevel.value
            .isNullOrBlank() || this.carbohydrateAmount
            .value.isNullOrBlank()
    }

    fun calculateInsulinDosage() {
        val carbohydrateAmountInt: Int = this.carbohydrateAmount.value.toString().toInt()
        val insulinStandardDosage: Int = (carbohydrateAmountInt * this.keFactor).roundToInt()
        val glucoseLevelDiscrepancy: Int =
            this.glucoseLevel.value.toString().toInt() - this.glucoseLevelUpperLimit
        // TODO ceil nutzen, damit aufgerundet wird
        val correctionStepsInt: Int =
            (glucoseLevelDiscrepancy / this.glucoseLevelCorrectionInterval)
        val insulinDosageRecommended =
            insulinStandardDosage + (correctionStepsInt * this.glucoseLevelCorrectionDosage)
        this.insulinDosageRecommended.value = insulinDosageRecommended.toString()

        Log.v(tag, "insulinDosageRecommended= " + this.insulinDosageRecommended.value.toString())
    }
}
