package com.github.diabetesassistant.auth.domain

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.data.CredentialsDTO
import com.github.diabetesassistant.auth.data.TokenDTO
import com.github.diabetesassistant.auth.data.UserCreationDTO
import com.github.diabetesassistant.auth.data.UserDTO
import java.lang.IllegalStateException
import java.util.UUID
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
            `when`(authClientMock.createToken(CredentialsDTO("foo@bar.com", "secret")))
                .thenReturn(Result.success(TokenDTO("access", "id")))
            val decodedJWT = mock(DecodedJWT::class.java)
            `when`(verifier.verify("id")).thenReturn(decodedJWT)

            val actual = testee.login(Credentials("foo@bar.com", "secret"))
            val expected = Result.success(Token("access", "id",decodedJWT))

            assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldReturnFailingResultWhenClientErrorOccurs() {
        runBlocking {
            val (authClientMock, _, testee) = fixtures()
            val error = IllegalStateException("foo")
            `when`(authClientMock.createToken(CredentialsDTO("foo@bar.com", "secret")))
                .thenReturn(Result.failure(error))

            val actual = testee.login(Credentials("foo@bar.com", "secret"))
            val expected = Result.failure<Token>(error)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldReturnFailingResultWhenJWTErrorOccurs() {
        runBlocking {
            val (authClientMock, verifier, testee) = fixtures()
            `when`(authClientMock.createToken(CredentialsDTO("foo@bar.com", "secret")))
                .thenReturn(Result.success(TokenDTO("access", "id")))
            val error = IllegalStateException("foo")
            `when`(verifier.verify("id")).thenThrow(error)

            val actual = testee.login(Credentials("foo@bar.com", "secret"))
            val expected = Result.failure<Token>(error)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldReturnRegisteredUser() {
        runBlocking {
            val (authClientMock, _, testee) = fixtures()
            val createdUser = UserDTO(UUID.randomUUID().toString(), "foo@bar.com", "patient")
            val dto = UserCreationDTO("foo@bar.com", "secret", "patient")
            `when`(authClientMock.createUser(dto)).thenReturn(Result.success(createdUser))

            val actual = testee.register(Credentials("foo@bar.com", "secret"))
            val userId = UUID.fromString(createdUser.id)
            val expected = Result.success(User(userId, createdUser.email))

            assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldReturnFailOnRegisterError() {
        runBlocking {
            val (authClientMock, _, testee) = fixtures()
            val dto = UserCreationDTO("foo@bar.com", "secret", "patient")
            val exception = Exception()
            `when`(authClientMock.createUser(dto)).thenReturn(Result.failure(exception))

            val actual = testee.register(Credentials("foo@bar.com", "secret"))
            val expected = Result.failure<User>(exception)

            assertEquals(expected, actual)
        }
    }
}
