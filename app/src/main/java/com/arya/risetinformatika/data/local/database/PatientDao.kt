package com.arya.risetinformatika.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.arya.risetinformatika.data.local.entity.Patient

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(patient: Patient)

    @Query("SELECT * from patient ORDER BY id DESC LIMIT 3")
    fun getNewestPatient(): LiveData<List<Patient>>

    @RawQuery(observedEntities = [Patient::class])
    fun getAllPatient(query: SupportSQLiteQuery): LiveData<List<Patient>>
}