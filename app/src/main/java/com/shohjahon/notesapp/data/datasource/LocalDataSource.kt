package com.shohjahon.notesapp.data.datasource

import com.shohjahon.notesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insert(noteEntity: NoteEntity)

    fun getByTime(time: Long): Flow<NoteEntity>

    fun getAll(): Flow<List<NoteEntity>>

    suspend fun delete(time: Long)

    fun getSize(): Flow<Int>
}