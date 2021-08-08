package com.github.diabetesassistant.doctor.data

data class AssignmentDTO(
    val code: String,
    val doctor: DoctorDTO?,
    val patient: PatientDTO?,
    val state: String
)
