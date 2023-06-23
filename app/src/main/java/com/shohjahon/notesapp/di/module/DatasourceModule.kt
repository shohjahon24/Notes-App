package com.shohjahon.notesapp.di.module

import com.shohjahon.notesapp.data.datasource.LocalDataSource
import com.shohjahon.notesapp.data.datasource.LocalDataSourceImpl
import com.shohjahon.notesapp.data.local.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(
        noteDao: NoteDao,
    ): LocalDataSource {
        return LocalDataSourceImpl(noteDao)
    }
}