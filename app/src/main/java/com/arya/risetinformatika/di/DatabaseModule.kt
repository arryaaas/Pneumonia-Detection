package com.arya.risetinformatika.di

import android.content.Context
import androidx.room.Room
import com.arya.risetinformatika.data.local.database.PatientDao
import com.arya.risetinformatika.data.local.database.PatientDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PatientDatabase = Room.databaseBuilder(
        context,
        PatientDatabase::class.java,
        "Patient.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun providePatientDao(database: PatientDatabase): PatientDao = database.patientDao()
}