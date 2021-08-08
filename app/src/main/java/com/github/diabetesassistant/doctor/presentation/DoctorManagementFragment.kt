package com.github.diabetesassistant.doctor.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.diabetesassistant.Dependencies.authService
import com.github.diabetesassistant.Dependencies.doctorService
import com.github.diabetesassistant.MainActivity
import com.github.diabetesassistant.R
import com.github.diabetesassistant.core.presentation.RedirectTimings
import com.github.diabetesassistant.databinding.FragmentDoctorManagementBinding
import com.github.diabetesassistant.doctor.domain.Assignment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoctorManagementFragment : Fragment() {
    private lateinit var viewModel: DoctorManagementViewModel
    private var _binding: FragmentDoctorManagementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(DoctorManagementViewModel::class.java)
        _binding = FragmentDoctorManagementBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val hintView: TextView = binding.doctorManagementHint
        hintView.visibility = View.INVISIBLE

        val confirmButton = binding.doctorManagementAssignButton
        confirmButton.isEnabled = false
        confirmButton.setOnClickListener(this::handleAssign)

        binding.doctorManagementConfirmationCode.doOnTextChanged(this::initiateAssignment)

        return root
    }

    @Suppress("UNUSED_PARAMETER")
    private fun initiateAssignment(chars: CharSequence?, a: Int, b: Int, c: Int) {
        this.viewModel.confirmationCode.value = chars.toString()
        val confirmationCode = this.viewModel.confirmationCode.value.toString()
        if (this.viewModel.isValid(confirmationCode)) {
            val button = this.binding.doctorManagementAssignButton
            val hintView = this.binding.doctorManagementHint
            val layout = this.binding.doctorManagementFragment
            val keySpace = getString(R.string.app_prefix)
            val sharedPref = this.activity?.getSharedPreferences(keySpace, Context.MODE_PRIVATE)
            val accessToken = sharedPref?.getString(getString(R.string.access_token_key), "")
            lifecycleScope.launch(Dispatchers.IO) {
                val assignment: Result<Assignment> = doctorService.findAssignment(
                    accessToken!!,
                    confirmationCode
                )
                assignment.fold(enable(button, hintView), handleError(layout))
            }
        }
    }

    private fun enable(button: Button, hintView: TextView): (Assignment) -> Unit {
        return {
            requireActivity().runOnUiThread {
                val email = it.doctor!!.email
                val textKey = R.string.doctor_management_confirmation_hint
                val text = "${requireActivity().getText(textKey)}\n\n$email"
                hintView.text = text
                hintView.visibility = View.VISIBLE
                button.isEnabled = true
            }
        }
    }

    private fun handleError(view: View): (Throwable) -> Unit {
        return { error: Throwable ->
            Log.e("AssignDoctor", error.stackTraceToString())
            val message = R.string.doctor_management_confirmation_failure
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleAssign(view: View) {
        val confirmationCode = this.viewModel.confirmationCode.value.toString()
        val keySpace = getString(R.string.app_prefix)
        val sharedPref = this.activity?.getSharedPreferences(keySpace, Context.MODE_PRIVATE)
        val accessToken = sharedPref?.getString(getString(R.string.access_token_key), "")
        val idToken = sharedPref?.getString(getString(R.string.id_token_key), "")
        val user = authService.authenticatedUser(idToken!!)
        val layout = this.binding.doctorManagementFragment
        lifecycleScope.launch(Dispatchers.IO) {
            val assignment = doctorService.confirmDoctorAssignment(
                accessToken!!,
                confirmationCode,
                user!!
            )
            assignment.fold(handleSuccessfulAssignment(layout), handleError(layout))
        }
    }

    private fun handleSuccessfulAssignment(view: View): (value: Assignment) -> Unit {
        return {
            Log.i("AssignDoctor", "Successfully assigned doctor")
            val message = R.string.doctor_management_confirmation_success
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
            Thread.sleep(RedirectTimings.ONE_SECOND)
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
