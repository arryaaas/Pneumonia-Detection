package com.arya.risetinformatika.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arya.risetinformatika.data.local.entity.Patient

@Database(
    entities = [Patient::class],
    version = 1,
    exportSchema = false
)
abstract class PatientDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
}