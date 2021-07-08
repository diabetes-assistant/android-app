package com.github.diabetesassistant.auth.data

import com.google.gson.Gson
import java.io.IOException
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class AuthClient(private val baseUrl: String) {
    private val client = OkHttpClient()
    private val gson = Gson()

    private fun backendCall(dto: DTO, path: String): Result<String> {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = gson.toJson(dto).toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url("${baseUrl}$path")
            .post(requestBody)
            .build()
        val requestCall: Result<Call> = Result.success(client.newCall(request))
        val response: Result<Response> = requestCall.mapCatching { it.execute() }.mapCatching {
            if (!it.isSuccessful) {
                val message = "Error got response with status code: ${it.code} body: ${it.message}"
                throw IOException(message)
            }
            it
        }
        return response.map { it.body!!.string() }
    }

    suspend fun createToken(credentials: CredentialsDTO): Result<TokenDTO> {
        val responseBody: Result<String> = backendCall(credentials, "auth/token")
        return responseBody.map { gson.fromJson(it, TokenDTO::class.java) }
    }

    suspend fun createUser(dto: UserCreationDTO): Result<UserDTO> {
        val responseBody: Result<String> = backendCall(dto, "user")
        return responseBody.map { gson.fromJson(it, UserDTO::class.java) }
    }
}
