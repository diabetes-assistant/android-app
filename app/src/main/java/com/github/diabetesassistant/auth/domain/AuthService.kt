package com.github.diabetesassistant.auth.domain

import com.auth0.jwt.JWTVerifier
import com.github.diabetesassistant.auth.data.AuthClient
import com.github.diabetesassistant.auth.data.UserDTO

class AuthService(private val authClient: AuthClient, private val verifier: JWTVerifier) {
    suspend fun login(user: User): Result<Token> {
        val tokenCreationResult = authClient.createToken(UserDTO(user.email, user.password))
        return tokenCreationResult.mapCatching {
            Token(it.accessToken, verifier.verify(it.idToken))
        }
    }
    // TODO hier muss dann mutmaßlich eine Funktion register implementiert werden
    /*
    suspend fun register(user: User) {
    }
    */
}
