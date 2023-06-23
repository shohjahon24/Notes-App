package com.shohjahon.notesapp.data.repository

import com.shohjahon.notesapp.data.datasource.LocalDataSource
import com.shohjahon.notesapp.data.mapper.toEntity
import com.shohjahon.notesapp.data.mapper.toHomeNoteList
import com.shohjahon.notesapp.data.mapper.toNote
import com.shohjahon.notesapp.domain.model.home.HomeNote
import com.shohjahon.notesapp.domain.model.note.Note
import com.shohjahon.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : NoteRepository {
    override fun getAllNotes(): Flow<List<HomeNote>> = localDataSource.getAll().map { it.toHomeNoteList() }
    override fun getSize(): Flow<Int> = localDataSource.getSize()


    override fun getNoteByTime(time: Long): Flow<Note> = localDataSource.getByTime(time).map { it.toNote() }


    override suspend fun createNote(note: Note){
        localDataSource.insert(note.toEntity())
    }

    override suspend fun deleteNote(time: Long) {
        localDataSource.delete(time)
    }
}