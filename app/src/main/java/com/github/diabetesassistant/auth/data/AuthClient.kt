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

    fun createToken(user: UserDTO): Result<TokenDTO> {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = gson.toJson(user).toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url("${baseUrl}auth/token")
            .post(requestBody)
            .build()
        val requestCall = Result.success(client.newCall(request))
        val response: Result<Response> = requestCall.map { it.execute() }.map {
            if (!it.isSuccessful) {
                val message = "Error got response with status code: ${it.code} body: ${it.message}"
                throw IOException(message)
            }
            it
        }
        val responseBody = response.map { it.body!!.string() }
        return responseBody.map { gson.fromJson(it, TokenDTO::class.java) }
    }
}
