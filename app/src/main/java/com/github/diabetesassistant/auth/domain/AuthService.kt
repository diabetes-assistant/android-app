package com.github.diabetesassistant.auth.domain

import android.util.Log
import com.auth0.jwt.JWTVerifier
import com.github.diabetesassistant.BuildConfig
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.data.UserDTO

class AuthService(private val authClient: AuthClient, private val verifier: JWTVerifier) {
    suspend fun login(user: User): Result<Token> {
        val tokenCreationResult = authClient.createToken(UserDTO(user.email, user.password))
        return tokenCreationResult.mapCatching {
            Token(it.accessToken, verifier.verify(it.idToken))
        }
    }
}
