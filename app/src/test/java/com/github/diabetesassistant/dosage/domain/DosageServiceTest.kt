package com.github.diabetesassistant.dosage.domain

import com.github.diabetesassistant.auth.data.UserDTO
import com.github.diabetesassistant.dosage.data.DosageClient
import com.github.diabetesassistant.dosage.data.InsulinPresetsDTO
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import kotlin.math.ceil
import kotlin.math.roundToInt

class DosageServiceTest {
    private fun fixtures(): Pair<DosageClient, DosageService> {
        val client: DosageClient = Mockito.mock(DosageClient::class.java)

        return Pair(client, DosageService(client))
    }

    @Test
    fun shouldReturnCalculatedDosage() {
        runBlocking {
            val (clientMock, testee) = fixtures()
            val user = UserDTO("123", "foo@email.com", "patient")
            val presets = Result.success(
                InsulinPresetsDTO(
                    0.5,
                    20,
                    2,
                    120,
                    260,
                    80
                )
            )
            Mockito.`when`(clientMock.getInsulinPresets(user)).thenReturn(presets)

            val actual = testee.calculateDosage("123", "foo@email.com", 150, 5)
            val expected = Result.success(7)

            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun shouldReturnAnotherDosage() {
        runBlocking {
            val (clientMock, testee) = fixtures()
            val user = UserDTO("123", "foo@email.com", "patient")
            val presets = Result.success(
                InsulinPresetsDTO(
                    0.8,
                    20,
                    3,
                    120,
                    260,
                    80
                )
            )
            Mockito.`when`(clientMock.getInsulinPresets(user)).thenReturn(presets)

            val actual = testee.calculateDosage("123", "foo@email.com", 90, 6)
            val expected = Result.success(2)

            Assert.assertEquals(expected, actual)
        }
    }
}
