package com.arya.risetinformatika.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Patient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "patient_name")
    var patientName: String? = null,

    @ColumnInfo(name = "diagnostic_result")
    var diagnosticResult: String
)