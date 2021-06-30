package com.github.diabetesassistant.auth.domain

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
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
    private fun fixtures(): Triple<AuthClient, JWTVerifier, AuthService> {
        val authClient: AuthClient = mock(AuthClient::class.java)
        val verifier: JWTVerifier = mock(JWTVerifier::class.java)

        return Triple(authClient, verifier, AuthService(authClient, verifier))
    }

    @Test
    fun shouldReturnTokenForLoginUser() {
        runBlocking {
            val (authClientMock, verifier, testee) = fixtures()
            `when`(authClientMock.createToken(UserDTO("foo@bar.com", "secret")))
                .thenReturn(Result.success(TokenDTO("access", "id")))
            val decodedJWT = mock(DecodedJWT::class.java)
            `when`(verifier.verify("id")).thenReturn(decodedJWT)

            val actual = testee.login(User("foo@bar.com", "secret"))
            val expected = Result.success(Token("access", decodedJWT))

            assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldReturnFailingResultWhenClientErrorOccurs() {
        runBlocking {
            val (authClientMock, _, testee) = fixtures()
            val error = IllegalStateException("foo")
            `when`(authClientMock.createToken(UserDTO("foo@bar.com", "secret")))
                .thenReturn(Result.failure(error))

            val actual = testee.login(User("foo@bar.com", "secret"))
            val expected = Result.failure<Token>(error)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldReturnFailingResultWhenJWTErrorOccurs() {
        runBlocking {
            val (authClientMock, verifier, testee) = fixtures()
            `when`(authClientMock.createToken(UserDTO("foo@bar.com", "secret")))
                .thenReturn(Result.success(TokenDTO("access", "id")))
            val error = IllegalStateException("foo")
            `when`(verifier.verify("id")).thenThrow(error)

            val actual = testee.login(User("foo@bar.com", "secret"))
            val expected = Result.failure<Token>(error)

            assertEquals(expected, actual)
        }
    }
}
