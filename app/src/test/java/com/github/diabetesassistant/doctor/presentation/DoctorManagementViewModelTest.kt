package com.github.diabetesassistant.doctor.presentation

import org.junit.Assert.assertEquals
import org.junit.Test

class DoctorManagementViewModelTest {
    @Test
    fun `should return true for valid confirmation code`() {
        val viewModel = DoctorManagementViewModel()
        val confirmationCode = "foo123"

        val actual = viewModel.isValid(confirmationCode)
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `should return false for not long enough confirmation code`() {
        val viewModel = DoctorManagementViewModel()
        val confirmationCode = "foo"

        val actual = viewModel.isValid(confirmationCode)
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `should return false for long enough but invalid confirmation code`() {
        val viewModel = DoctorManagementViewModel()
        val confirmationCode = "foo!ar"

        val actual = viewModel.isValid(confirmationCode)
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `should return false for too long confirmation code`() {
        val viewModel = DoctorManagementViewModel()
        val confirmationCode = "foobarfoobar"

        val actual = viewModel.isValid(confirmationCode)
        val expected = false

        assertEquals(expected, actual)
    }
}
