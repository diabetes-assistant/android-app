package com.github.diabetesassistant.auth.domain

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.data.UserDTO

private const val ONE_SECOND: Long = 1
private const val FIVE_SECONDS: Long = 5

class AuthService(private val authClient: AuthClient, idTokenSecret: String) {
    private val algorithm: Algorithm = Algorithm.HMAC512(idTokenSecret)
    private val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer("diabetes-assistant-backend")
        .withAudience("diabetes-assistant-client")
        .acceptLeeway(ONE_SECOND)
        .acceptExpiresAt(FIVE_SECONDS)
        .build()

    suspend fun login(user: User): Result<Token> {
        val tokenCreationResult = authClient.createToken(UserDTO(user.email, user.password))
        return tokenCreationResult.mapCatching {
            val idToken: DecodedJWT = verifier.verify(it.idToken)
            Token(it.accessToken, idToken)
        }
    }
}
