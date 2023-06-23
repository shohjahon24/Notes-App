package com.shohjahon.notesapp.data.datasource

import com.shohjahon.notesapp.data.local.dao.NoteDao
import com.shohjahon.notesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
) : LocalDataSource {
    override suspend fun insert(noteEntity: NoteEntity) {
       noteDao.insert(noteEntity)
    }

    override fun getByTime(time: Long): Flow<NoteEntity> {
       return noteDao.getByTime(time)
    }

    override fun getAll(): Flow<List<NoteEntity>> {
       return noteDao.getAll()
    }

    override suspend fun delete(time: Long) {
        noteDao.delete(time)
    }

    override fun getSize(): Flow<Int> {
        return noteDao.getSize()
    }

}