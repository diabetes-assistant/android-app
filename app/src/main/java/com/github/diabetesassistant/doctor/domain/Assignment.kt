package com.github.diabetesassistant.doctor.domain

data class Assignment(
    val code: String,
    val doctor: Doctor?,
    val patient: Patient?,
    val state: String
)
