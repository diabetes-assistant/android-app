package com.github.diabetesassistant

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.domain.AuthService

object Dependencies {
    private const val ONE_SECOND: Long = 1
    private const val FIVE_SECONDS: Long = 5
    private const val BASE_URL = "https://live-diabetes-assistant-be.herokuapp.com/"

    private val algorithm: Algorithm = Algorithm.HMAC512(BuildConfig.ID_TOKEN_SECRET)
    private val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer("diabetes-assistant-backend")
        .withAudience("diabetes-assistant-client")
        .acceptLeeway(ONE_SECOND)
        .acceptExpiresAt(FIVE_SECONDS)
        .build()
    private val authClient = AuthClient(BASE_URL)
    val authService = AuthService(authClient, verifier)
}
