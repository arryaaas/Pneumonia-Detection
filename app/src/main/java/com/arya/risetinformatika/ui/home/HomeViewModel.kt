package com.arya.risetinformatika.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arya.risetinformatika.data.Repository
import com.arya.risetinformatika.data.local.entity.Patient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _patientResult = MutableLiveData<List<Patient>>()
    val patientResult: LiveData<List<Patient>> = _patientResult

    fun getNewestPatient() {
        repository.getNewestPatient().observeForever {
            _patientResult.value = it
        }
    }
}