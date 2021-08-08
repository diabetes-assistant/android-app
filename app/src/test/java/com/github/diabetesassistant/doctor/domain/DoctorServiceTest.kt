package com.github.diabetesassistant.doctor.domain

import com.github.diabetesassistant.doctor.data.AssignmentDTO
import com.github.diabetesassistant.doctor.data.DoctorClient
import com.github.diabetesassistant.doctor.data.DoctorDTO
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class DoctorServiceTest {
    private fun fixtures(): Pair<DoctorClient, DoctorService> {
        val client: DoctorClient = Mockito.mock(DoctorClient::class.java)

        return Pair(client, DoctorService(client))
    }

    @Test
    fun `should return existing assignment`() {
        runBlocking {
            val (clientMock, testee) = fixtures()
            val doctorId = UUID.randomUUID()
            val doctorDTO = DoctorDTO(doctorId.toString(), "foo@bar.com")
            val dto = AssignmentDTO("foobar", doctorDTO, null, "initial")
            val code = "foobar"
            val accessToken = "accessToken"
            `when`(clientMock.getAssignment(accessToken, code)).thenReturn(Result.success(dto))

            val actual = testee.findAssignment(accessToken, code)
            val doctor = Doctor(doctorId, doctorDTO.email)
            val expected = Result.success(Assignment(dto.code, doctor, null, dto.state))

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return error when assignment is invalid`() {
        runBlocking {
            val (clientMock, testee) = fixtures()
            val dto = AssignmentDTO("foobar", null, null, "initial")
            val code = "foobar"
            val accessToken = "accessToken"
            `when`(clientMock.getAssignment(accessToken, code)).thenReturn(Result.success(dto))

            val actual = testee.findAssignment(accessToken, code)

            assertTrue(actual.isFailure)
        }
    }
}
