package com.github.diabetesassistant.doctor.domain

import com.github.diabetesassistant.auth.domain.User
import com.github.diabetesassistant.doctor.data.AssignmentDTO
import com.github.diabetesassistant.doctor.data.DoctorClient
import com.github.diabetesassistant.doctor.data.DoctorDTO
import com.github.diabetesassistant.doctor.data.PatientDTO
import java.util.UUID

class DoctorService(val doctorClient: DoctorClient) {
    private fun toAssignmentWithDoctor(dto: AssignmentDTO): Assignment {
        if (dto.doctor == null) {
            throw IllegalArgumentException("No doctor associated with assignment")
        }
        val doctor = Doctor(
            UUID.fromString(dto.doctor.id),
            dto.doctor.email
        )
        return Assignment(dto.code, doctor, null, dto.state)
    }

    private fun toAssignmentWithDoctorAndPatient(dto: AssignmentDTO): Assignment {
        val assignmentWithDoctor = this.toAssignmentWithDoctor(dto)
        if (dto.patient == null) {
            throw IllegalArgumentException("No patient associated with assignment")
        }
        val patient = Patient(
            UUID.fromString(dto.patient.id),
            dto.patient.email
        )
        return Assignment(dto.code, assignmentWithDoctor.doctor, patient, dto.state)
    }

    suspend fun findAssignment(accessToken: String, code: String): Result<Assignment> {
        val assignment = this.doctorClient.getAssignment(accessToken, code)
        return assignment.mapCatching(this::toAssignmentWithDoctor)
    }

    suspend fun confirmDoctorAssignment(
        accessToken: String,
        code: String,
        user: User
    ): Result<Assignment> {
        val updatedAssignment = findAssignment(accessToken, code).map {
            val doctor = DoctorDTO(it.doctor!!.id.toString(), it.doctor.email)
            val patient = PatientDTO(user.id.toString(), user.email)
            AssignmentDTO(it.code, doctor, patient, "patientConfirmed")
        }.getOrThrow()
        return doctorClient
            .putAssignment(accessToken, updatedAssignment)
            .mapCatching(this::toAssignmentWithDoctorAndPatient)
    }
}
