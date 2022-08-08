package com.hxl.arithcalc.di

import android.content.Context
import androidx.room.Room
import com.hxl.data.repository.EquationHistoryRepositoryImpl
import com.hxl.data.repository.PreferenceRepositoryImpl
import com.hxl.data.storage.PreferenceStorage
import com.hxl.data.storage.room.LocalDatabase
import com.hxl.data.storage.sharedpref.SharedPreferenceStorage
import com.hxl.domain.repository.EquationHistoryRepository
import com.hxl.domain.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePreferenceStorage(@ApplicationContext context: Context): PreferenceStorage {
        return SharedPreferenceStorage(context)
    }

    @Provides
    @Singleton
    fun providePreferenceRepository(preferenceStorage: PreferenceStorage): PreferenceRepository {
        return PreferenceRepositoryImpl(preferenceStorage)
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            "arithmathics.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideEquationHistoryRepository(database: LocalDatabase): EquationHistoryRepository {
        return EquationHistoryRepositoryImpl(database.equationHistoryDao())
    }

}