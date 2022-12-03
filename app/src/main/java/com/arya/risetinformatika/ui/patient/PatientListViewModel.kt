package com.arya.risetinformatika.ui.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.arya.risetinformatika.data.local.entity.Patient
import com.arya.risetinformatika.data.Repository
import com.arya.risetinformatika.helper.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(
private val repository: Repository
) : ViewModel() {
    private val _filter = MutableLiveData<FilterType>()
    val filter: LiveData<FilterType> = _filter

    private val _patientResult = MutableLiveData<List<Patient>>()
    val patientResult: LiveData<List<Patient>> = _patientResult

    init {
        _filter.value = FilterType.ALL
    }

    fun changeSortType(filterType: FilterType) {
        _filter.value = filterType
    }

    fun getAllPatient() {
        _filter.switchMap { sortType ->
            repository.getAllPatient(sortType)
        }.observeForever {
            _patientResult.value = it
        }
    }
}