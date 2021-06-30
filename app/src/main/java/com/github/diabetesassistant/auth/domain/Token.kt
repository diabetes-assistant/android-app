package com.github.diabetesassistant.auth.domain

import com.auth0.jwt.interfaces.DecodedJWT

data class Token(val accessToken: String, val idToken: DecodedJWT)
