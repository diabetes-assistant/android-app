package com.github.diabetesassistant.dosage.domain

import com.github.diabetesassistant.dosage.data.DosageClient

class DosageService(private val client: DosageClient) {
    fun calculateDosage(
        userId: String,
        email: String,
        glucoseLevel: Int,
        carbohydrateUnits: Int
    ): Result<Int> {
        return Result.failure(Exception())
    }
}
