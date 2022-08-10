package com.hxl.arithcalc.di

import com.hxl.domain.repository.EquationHistoryRepository
import com.hxl.domain.repository.PreferenceRepository
import com.hxl.domain.usecase.database.history.InsertEquationHistory
import com.hxl.domain.usecase.database.history.ReadEquationHistory
import com.hxl.domain.usecase.prefs.GetTheme
import com.hxl.domain.usecase.prefs.SaveTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    // region prefs
    @Provides
    fun provideGetTheme(preferenceRepository: PreferenceRepository): GetTheme {
        return GetTheme(preferenceRepository)
    }

    @Provides
    fun provideSaveTheme(preferenceRepository: PreferenceRepository): SaveTheme {
        return SaveTheme(preferenceRepository)
    }
    // endregion

    // region equation history
    @Provides
    fun provideEquationHistory(equationHistoryRepository: EquationHistoryRepository): ReadEquationHistory{
        return ReadEquationHistory(equationHistoryRepository)
    }

    @Provides
    fun provideInsertEquationHistory(equationHistoryRepository: EquationHistoryRepository): InsertEquationHistory {
        return InsertEquationHistory(equationHistoryRepository)
    }
    // endregion
}