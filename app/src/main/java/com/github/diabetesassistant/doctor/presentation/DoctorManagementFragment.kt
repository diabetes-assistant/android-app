package com.github.diabetesassistant.doctor.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.databinding.FragmentDoctorManagementBinding

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
