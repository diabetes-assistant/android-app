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

    // TODO Hier noch klären, ob schon Blutzuckerober- und -untergrenzen berücksichtigt werden sollen
    // TODO Gibt es noch andere Eingaben, die invalid sind?
    fun isInvalid(): Boolean {
        return this.glucoseLevel.value
            .isNullOrBlank() || this.carbohydrateAmount
            .value.isNullOrBlank()
    }

    /**
     * Berechnung der Insulindosis
     * TODO es darf natürlich keine negativen Dosisempfehlungen geben
     * TODO Hier die Grenzwerte und die entsprechenden Warnungen/
     * TODO Empfehlungen implentieren, wenn diese über-/ unterschritten werden?
     */
    fun calculateInsulinDosage() {
        val carbohydrateAmountInt: Int = this.carbohydrateAmount.value.toString().toInt()
        val insulinStandardDosage: Int = (carbohydrateAmountInt * Presets.keFactor).roundToInt()
        val glucoseLevelDiscrepancy: Int =
            this.glucoseLevel.value.toString().toInt() - Presets.glucoseLevelUpperNormalLimit
        // TODO ceil nutzen, damit aufgerundet wird
        val correctionStepsInt: Int =
            (glucoseLevelDiscrepancy / Presets.glucoseLevelCorrectionInterval)
        val insulinDosageRecommended =
            insulinStandardDosage + (correctionStepsInt * Presets.glucoseLevelCorrectionDosage)
        this.insulinDosageRecommended.value = insulinDosageRecommended.toString()
        Log.v(tag, "insulinDosageRecommended= " + this.insulinDosageRecommended.value.toString())
    }

    /**  Durch die Ärzt:in voreingestellte Felder
     * Hier zu Testzwecken als Konstanten gesetzt
     *
     */
    companion object Presets {
        // Kohlenhydrateinheit
        // The amount of insulin [IE] which is needed to normalize the blood glucose level
        // per intake of 1 carbohydrate unit (10 g of carbohydrates)
        // Will finally be entered by the doctor but is arbitrarily set here to 0.5 for testing purposes
        // TODO sollte durch Ärzt:in gesetzt werden
        const val keFactor: Float = 0.5F

        // Größe eines Korrekturintervalls/ Korrekturschrittes in [mg/dl]
        // TODO sollte durch Ärzt:in gesetzt werden, hier aber zu Testzwecken erstmal auf 20 mg/dl gesetzt
        const val glucoseLevelCorrectionInterval: Int = 20

        // Korrektureinheiten: Insulineinheiten [IE], die pro Korrekturinterval/ Korrekturschritt erforderlich sind
        // TODO sollte durch Ärzt:in gesetzt werden, hier aber erstmal auf 2 IE gesetzt
        const val glucoseLevelCorrectionDosage: Int = 2

        // Oberer Normwert für den Blutzuckerspiegel, i.d.R. 120 mg/dl
        // TODO dieser obere Blutzuckernormwert sollte durch Ärzt:in änderbar sein,
        // TODO hier aber erstmal auf 120 mg/dl gesetzt
        const val glucoseLevelUpperNormalLimit: Int = 120

        // TODO Ober- und Untergrenzen, deren Unter-/ Überschreitung Arztkontakt erforderlich machen
        const val glucoseLevelOuterBoundsHigh: Int = 260

        const val glucoseLevelOuterBoundsLow: Int = 80
    }
}
