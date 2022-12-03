package com.arya.risetinformatika.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: Data,
)

data class Data(
    @field:SerializedName("patient_name")
    val patientName: String,

    @field:SerializedName("diagnostic_result")
    val diagnosticResult: String,
)
