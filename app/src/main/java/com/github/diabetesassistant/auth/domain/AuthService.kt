package com.github.diabetesassistant.auth.domain

import com.auth0.jwt.JWTVerifier
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.data.CredentialsDTO
import com.github.diabetesassistant.auth.data.UserDTO
import java.util.UUID

class AuthService(private val authClient: AuthClient, private val verifier: JWTVerifier) {
    suspend fun login(credentials: Credentials): Result<Token> {
        val dto = CredentialsDTO(credentials.email, credentials.password)
        val tokenCreationResult = authClient.createToken(dto)
        return tokenCreationResult.mapCatching {
            Token(it.accessToken, verifier.verify(it.idToken))
        }
    }

    suspend fun register(credentials: Credentials): Result<User> {
        val credentialsDTO = CredentialsDTO(credentials.email, credentials.password)
        val createdUserDTO: Result<UserDTO> = authClient.createUser(credentialsDTO)
        return createdUserDTO.mapCatching {
            User(UUID.fromString(it.id), it.email)
        }
    }
}
