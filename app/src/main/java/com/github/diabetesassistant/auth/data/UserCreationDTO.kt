package com.github.diabetesassistant.auth.data

data class UserCreationDTO(val email: String, val password: String, val role: String) : DTO
