package com.github.diabetesassistant

import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.After
import org.junit.Before

open class BackendIntegrationTest(
    private val isRecording: Boolean,
    private val baseUrl: String
) {
    private val wireMock = WireMockServer(8080)
    @Before
    fun setUp() {
        wireMock.start()
        if (isRecording) {
            wireMock.startRecording(baseUrl)
        }
    }

    @After
    fun tearDown() {
        if (isRecording) {
            wireMock.saveMappings()
            wireMock.stopRecording()
        }
        wireMock.stop()
    }
}
