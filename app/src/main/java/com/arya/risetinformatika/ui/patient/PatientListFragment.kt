package com.arya.risetinformatika.ui.patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arya.risetinformatika.adapter.PatientAdapter
import com.arya.risetinformatika.databinding.FragmentPatientListBinding
import com.arya.risetinformatika.helper.FilterType
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientListFragment : Fragment() {
    private var _binding: FragmentPatientListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PatientListViewModel by viewModels()

    private lateinit var patientAdapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllPatient()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpRecyclerView()
        setUpAction()
        observerPatientResult()
    }

    private fun setUpView() {
        (binding.chipGroup.getChildAt(
            when (viewModel.filter.value) {
                FilterType.ALL -> 0
                FilterType.SAFE -> 1
                else -> 2
            }
        ) as Chip).isChecked = true
    }

    private fun setUpRecyclerView() {
        patientAdapter = PatientAdapter()
        binding.rvPatient.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPatient.adapter = patientAdapter
    }

    private fun setUpAction() {
        binding.imgBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val checkedChip = group.findViewById<Chip>(checkedIds[0])
            viewModel.changeSortType(
                when (checkedChip.text.toString()) {
                    "All" -> FilterType.ALL
                    "Safe" -> FilterType.SAFE
                    else -> FilterType.PNEUMONIA
                }
            )
        }
    }

    private fun observerPatientResult() {
        viewModel.patientResult.observe(viewLifecycleOwner) {
            patientAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}