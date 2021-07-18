package com.github.diabetesassistant.dosage.data

data class InsulinPresetsDTO(
    val keFactor: Double,
    val glucoseLevelCorrectionInterval: Int,
    val glucoseLevelCorrectionDosage: Int,
    val glucoseLevelUpperNormalLimit: Int,
    val glucoseLevelOuterBoundsHigh: Int,
    val glucoseLevelOuterBoundsLow: Int
)
