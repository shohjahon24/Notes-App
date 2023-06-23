package com.shohjahon.notesapp.di.module

import com.shohjahon.notesapp.data.datasource.LocalDataSource
import com.shohjahon.notesapp.data.repository.NoteRepositoryImpl
import com.shohjahon.notesapp.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalDataSource
    ): NoteRepository {
        return NoteRepositoryImpl(localDataSource)
    }
}