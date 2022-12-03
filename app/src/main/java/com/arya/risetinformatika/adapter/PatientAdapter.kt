package com.arya.risetinformatika.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arya.risetinformatika.data.local.entity.Patient
import com.arya.risetinformatika.databinding.ItemPatientBinding
import com.arya.risetinformatika.helper.PatientDiffCallback

class PatientAdapter : ListAdapter<Patient, PatientAdapter.PatientViewHolder>(PatientDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        return PatientViewHolder(
            ItemPatientBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = getItem(position)
        holder.bind(patient)
    }

    inner class PatientViewHolder(private val binding: ItemPatientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(patient: Patient) {
            with(binding) {
                tvPatientName.text = patient.patientName
                tvDiagnosticResult.text = patient.diagnosticResult
            }
        }
    }
}