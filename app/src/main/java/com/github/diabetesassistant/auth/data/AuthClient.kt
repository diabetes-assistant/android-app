package com.github.diabetesassistant.auth.data

class AuthClient {
    fun createToken(user: UserDTO): Result<TokenDTO> {
        return Result.failure(NotImplementedError())
    }
}
