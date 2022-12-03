package com.arya.risetinformatika.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arya.risetinformatika.R
import com.arya.risetinformatika.adapter.PatientAdapter
import com.arya.risetinformatika.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var patientAdapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNewestPatient()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpAction()
        observePatientResult()
    }

    private fun setUpRecyclerView() {
        patientAdapter = PatientAdapter()
        binding.rvPatient.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPatient.adapter = patientAdapter
    }

    private fun setUpAction() {
        binding.btnDiagnosis.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_diagnosisFragment
            )
        }
        binding.tvSeeAll.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_patientListFragment
            )
        }
    }

    private fun observePatientResult() {
        viewModel.patientResult.observe(viewLifecycleOwner) {
            patientAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}