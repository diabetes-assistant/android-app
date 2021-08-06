package com.github.diabetesassistant.dosage.data

import com.github.diabetesassistant.auth.data.UserDTO

class DosageClient {
    @Suppress("UNUSED_PARAMETER")
    fun getInsulinPresets(user: UserDTO): Result<InsulinPresetsDTO> {
        return Result.failure(Exception())
    }
}
