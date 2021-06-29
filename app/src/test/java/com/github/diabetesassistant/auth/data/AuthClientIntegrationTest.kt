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

            val actual = client.createToken(UserDTO("doctor@email.com", "secret"))
            val expected = Result.success(TokenDTO("accessToken", "idToken"))

            assertEquals(expected, actual)
        }
    }
}
