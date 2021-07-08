package com.github.diabetesassistant.auth.data

import com.google.gson.Gson
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class AuthClient(private val baseUrl: String) {
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun createToken(credentials: CredentialsDTO): Result<TokenDTO> {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = gson.toJson(credentials).toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url("${baseUrl}auth/token")
            .post(requestBody)
            .build()
        val requestCall = Result.success(client.newCall(request))
        val response: Result<Response> = requestCall.mapCatching { it.execute() }.mapCatching {
            if (!it.isSuccessful) {
                val message = "Error got response with status code: ${it.code} body: ${it.message}"
                throw IOException(message)
            }
            it
        }
        val responseBody = response.map { it.body!!.string() }
        return responseBody.map { gson.fromJson(it, TokenDTO::class.java) }
    }

    suspend fun createUser(credentials: CredentialsDTO): Result<UserDTO> {
        return Result.failure(Exception())
    }
}
