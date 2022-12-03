package com.arya.risetinformatika.helper

import androidx.recyclerview.widget.DiffUtil
import com.arya.risetinformatika.data.local.entity.Patient

class PatientDiffCallback : DiffUtil.ItemCallback<Patient>() {
    override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem == newItem
    }
}