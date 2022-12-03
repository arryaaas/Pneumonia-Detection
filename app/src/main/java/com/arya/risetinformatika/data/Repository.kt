package com.arya.risetinformatika.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.arya.risetinformatika.data.local.entity.Patient
import com.arya.risetinformatika.data.local.database.PatientDao
import com.arya.risetinformatika.data.remote.response.ErrorResponse
import com.arya.risetinformatika.data.remote.response.PredictResponse
import com.arya.risetinformatika.data.remote.retrofit.ApiService
import com.arya.risetinformatika.helper.FilterType
import com.arya.risetinformatika.helper.FilterUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val patientDao: PatientDao
) {
    fun predict(
        file: MultipartBody.Part,
        patientName: RequestBody
    ): LiveData<Result<PredictResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.predict(file, patientName)
            emit(Result.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val customErrorResponse: ErrorResponse? =
                        Gson().fromJson(e.response()?.errorBody()?.charStream(), type)
                    emit(Result.Error(customErrorResponse?.message.toString()))
                }
                is IOException -> {
                    emit(Result.Error("Please check your network connection"))
                }
                else -> {
                    emit(Result.Error("An unexpected error occurred"))
                }
            }
        }
    }

    suspend fun insertPatient(patient: Patient) {
        patientDao.insert(patient)
    }

    fun getNewestPatient(): LiveData<List<Patient>> = patientDao.getNewestPatient()

    fun getAllPatient(filterType: FilterType): LiveData<List<Patient>> {
        val query = FilterUtils.getFilteredQuery(filterType)
        return patientDao.getAllPatient(query)
    }
}