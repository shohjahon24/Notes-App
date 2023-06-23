package com.shohjahon.notesapp.domain.repository

import com.shohjahon.notesapp.domain.model.home.HomeNote
import com.shohjahon.notesapp.domain.model.note.Note
import kotlinx.coroutines.flow.Flow


interface NoteRepository {

    fun getAllNotes(
    ): Flow<List<HomeNote>>

    fun getSize(
    ): Flow<Int>

    fun getNoteByTime(
        time: Long
    ): Flow<Note>

    suspend fun createNote(
        note: Note
    )

    suspend fun deleteNote(
        time: Long
    )

}