package com.github.diabetesassistant.dosage.domain

import com.github.diabetesassistant.auth.data.UserDTO
import com.github.diabetesassistant.dosage.data.DosageClient
import kotlin.math.ceil
import kotlin.math.roundToInt

class DosageService(private val client: DosageClient) {
    fun calculateDosage(
        userId: String,
        email: String,
        glucoseLevel: Int,
        carbohydrateUnits: Int
    ): Result<Int> {
        val insulinPresets = client.getInsulinPresets(UserDTO(userId, email, "patient"))
        return insulinPresets.map {
            val glucoseDiscrepancy = glucoseLevel - it.glucoseLevelUpperNormalLimit
            val steps = ceil(glucoseDiscrepancy / it.glucoseLevelCorrectionInterval.toDouble())
            val standardDosage = (carbohydrateUnits * it.keFactor).roundToInt()
            val correctedDosage = standardDosage + steps * it.glucoseLevelCorrectionDosage
            return Result.success(correctedDosage.toInt())
        }
    }
}
