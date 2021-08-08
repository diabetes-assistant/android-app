package com.github.diabetesassistant.doctor.domain

import com.github.diabetesassistant.auth.domain.User
import com.github.diabetesassistant.doctor.data.AssignmentDTO
import com.github.diabetesassistant.doctor.data.DoctorClient
import com.github.diabetesassistant.doctor.data.DoctorDTO
import com.github.diabetesassistant.doctor.data.PatientDTO
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class DoctorServiceTest {
    private fun fixtures(): Pair<DoctorClient, DoctorService> {
        val client: DoctorClient = mock(DoctorClient::class.java)

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

    @Test
    fun `should return assignment when successfully confirmed`() {
        runBlocking {
            val (clientMock, testee) = fixtures()
            val user = User(UUID.randomUUID(), "patient@email.com")
            val doctorId = UUID.randomUUID()
            val doctor = DoctorDTO(doctorId.toString(), "doctor@email.com")
            val patient = PatientDTO(user.id.toString(), user.email)
            val initialDTO = AssignmentDTO("foobar", doctor, null, "initial")
            val confirmedDTO = AssignmentDTO("foobar", doctor, patient, "patientConfirmed")
            val code = "foobar"
            val accessToken = "accessToken"
            `when`(clientMock.getAssignment(accessToken, code))
                .thenReturn(Result.success(initialDTO))
            `when`(clientMock.putAssignment(accessToken, confirmedDTO))
                .thenReturn(Result.success(confirmedDTO))

            val actual = testee.confirmDoctorAssignment(accessToken, code, user)
            val expectedDoctor = Doctor(doctorId, doctor.email)
            val expectedPatient = Patient(user.id, user.email)
            val expectedAssignment = Assignment(
                confirmedDTO.code,
                expectedDoctor,
                expectedPatient,
                confirmedDTO.state
            )
            val expected = Result.success(expectedAssignment)

            assertEquals(expected, actual)
        }
    }
}
