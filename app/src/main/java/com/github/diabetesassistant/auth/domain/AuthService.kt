package com.github.diabetesassistant.auth.domain

import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.data.UserDTO

class AuthService(private val authClient: AuthClient) {
    suspend fun login(user: User): Result<Token> {
        val tokenCreationResult = authClient.createToken(UserDTO(user.email, user.password))
        return tokenCreationResult.map {
            Token(it.accessToken, it.idToken)
        }
    }
}
