package com.github.diabetesassistant.doctor.domain

import com.github.diabetesassistant.doctor.data.DoctorClient
import java.util.UUID

class DoctorService(val doctorClient: DoctorClient) {
    suspend fun findAssignment(accessToken: String, code: String): Result<Assignment> {
        val assignment = this.doctorClient.getAssignment(accessToken, code)
        return assignment.mapCatching {
            if (it.doctor == null) {
                throw IllegalArgumentException("No doctor associated with assignment")
            }
            val doctor = Doctor(
                UUID.fromString(it.doctor.id),
                it.doctor.email
            )
            Assignment(it.code, doctor, null, it.state)
        }
    }

//    suspend fun confirmDoctorAssignment(code: String, user: User): Result<> {}
}
