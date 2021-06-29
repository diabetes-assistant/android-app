package com.github.diabetesassistant.auth.domain

import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.data.TokenDTO
import com.github.diabetesassistant.auth.data.UserDTO
import java.lang.IllegalStateException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class AuthServiceTest {
    private fun fixtures(): Pair<AuthClient, AuthService> {
        val authClient: AuthClient = mock(AuthClient::class.java)

        return Pair(authClient, AuthService(authClient))
    }

    @Test
    fun shouldReturnTokenForLoginUser() {
        runBlocking {
            val (authClientMock, testee) = fixtures()
            `when`(authClientMock.createToken(UserDTO("foo@bar.com", "secret")))
                .thenReturn(Result.success(TokenDTO("access", "id")))

            val actual = testee.login(User("foo@bar.com", "secret"))
            val expected = Result.success(Token("access", "id"))

            assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldReturnFailingResultWhenErrorOccurs() {
        runBlocking {
            val (authClientMock, testee) = fixtures()
            val error = IllegalStateException("foo")
            `when`(authClientMock.createToken(UserDTO("foo@bar.com", "secret")))
                .thenReturn(Result.failure(error))

            val actual = testee.login(User("foo@bar.com", "secret"))
            val expected = Result.failure<Token>(error)

            assertEquals(expected, actual)
        }
    }
}
