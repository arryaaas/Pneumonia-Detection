package com.arya.risetinformatika.ui.diagnosis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.risetinformatika.data.local.entity.Patient
import com.arya.risetinformatika.data.Result
import com.arya.risetinformatika.data.Repository
import com.arya.risetinformatika.data.remote.response.PredictResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class DiagnosisViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _predictResult = MutableLiveData<Result<PredictResponse>>()
    val predictResult: LiveData<Result<PredictResponse>> = _predictResult

    fun predict(
        file: MultipartBody.Part,
        patientName: RequestBody
    ) {
        repository.predict(file, patientName).observeForever {
            _predictResult.value = it
        }
    }

    fun insertPatient(patient: Patient) {
        viewModelScope.launch {
            repository.insertPatient(patient)
        }
    }
}