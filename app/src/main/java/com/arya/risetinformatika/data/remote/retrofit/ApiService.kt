package com.arya.risetinformatika.data.remote.retrofit

import com.arya.risetinformatika.data.remote.response.PredictResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/predict")
    suspend fun predict(
        @Part file: MultipartBody.Part,
        @Part("patient_name") patientName: RequestBody,
    ): PredictResponse
}