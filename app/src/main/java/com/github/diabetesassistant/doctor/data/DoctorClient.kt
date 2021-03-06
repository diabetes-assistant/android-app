package com.github.diabetesassistant.doctor.data

import com.google.gson.Gson
import java.io.IOException
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class DoctorClient(private val baseUrl: String) {
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun getAssignment(accessToken: String, code: String): Result<AssignmentDTO> {
        val request: Request = Request.Builder()
            .url("${baseUrl}assignment/$code")
            .header("Authorization", "Bearer $accessToken")
            .get()
            .build()
        val requestCall: Result<Call> = Result.success(client.newCall(request))
        val response: Result<Response> = requestCall.mapCatching { it.execute() }.mapCatching {
            if (!it.isSuccessful) {
                val message = "Error got response with status code: ${it.code} body: ${it.message}"
                throw IOException(message)
            }
            it
        }
        return response
            .map { it.body!!.string() }
            .map { gson.fromJson(it, AssignmentDTO::class.java) }
    }

    fun putAssignment(accessToken: String, dto: AssignmentDTO): Result<AssignmentDTO> {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = gson.toJson(dto).toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url("${baseUrl}assignment/${dto.code}")
            .header("Authorization", "Bearer $accessToken")
            .put(requestBody)
            .build()
        val requestCall: Result<Call> = Result.success(client.newCall(request))
        val response: Result<Response> = requestCall.mapCatching { it.execute() }.mapCatching {
            if (!it.isSuccessful) {
                val message = "Error got response with status code: ${it.code} body: ${it.message}"
                throw IOException(message)
            }
            it
        }
        return response
            .map { it.body!!.string() }
            .map { gson.fromJson(it, AssignmentDTO::class.java) }
    }
}
