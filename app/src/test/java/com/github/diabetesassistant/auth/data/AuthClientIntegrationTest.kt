package com.github.diabetesassistant.auth.data

import com.github.diabetesassistant.BackendIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthClientIntegrationTest :
    BackendIntegrationTest(false, "https://staging-diabetes-assistant-be.herokuapp.com") {

    @Test
    fun shouldCreateToken() {
        runBlocking {
            val client = AuthClient("http://localhost:8080/")

            val actual = client.createToken(CredentialsDTO("doctor@email.com", "secret"))
            val expected = Result.success(TokenDTO("accessToken", "idToken"))

            assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldCreateUser() {
        runBlocking {
            val client = AuthClient("http://localhost:8080/")
            val dto = UserCreationDTO("foo@email.com", "secret", "patient")

            val actual = client.createUser(dto)
            val createdUser = UserDTO(
                "fa171f58-e36f-48ca-b34f-185b4e810bac",
                "foo@email.com",
                "patient"
            )
            val expected = Result.success(createdUser)

            assertEquals(expected, actual)
        }
    }
}
