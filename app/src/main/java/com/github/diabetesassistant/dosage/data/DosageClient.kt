package com.github.diabetesassistant.dosage.data

import com.github.diabetesassistant.auth.data.UserDTO

class DosageClient {
    fun getInsulinPresets(user: UserDTO): Result<InsulinPresetsDTO> {
        return Result.failure(Exception())
    }
}
