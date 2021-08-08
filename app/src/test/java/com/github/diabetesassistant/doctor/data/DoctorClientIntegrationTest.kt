package com.github.diabetesassistant.doctor.data

import com.github.diabetesassistant.BackendIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class DoctorClientIntegrationTest :
    BackendIntegrationTest(false, "https://staging-diabetes-assistant-be.herokuapp.com") {

    @Test
    fun shouldGetAssignmentToken() {
        runBlocking {
            val client = DoctorClient("http://localhost:8080/")
            val code = "SI0YG6"
            val accessToken = "accessToken"

            val actual = client.getAssignment(accessToken, code)
            val doctor = DoctorDTO("0b5b7136-a359-4423-b9d0-0305dd946a26", "doctor@email.com")
            val expected = Result.success(AssignmentDTO(code, doctor, null, "initial"))

            assertEquals(expected, actual)
        }
    }
}
