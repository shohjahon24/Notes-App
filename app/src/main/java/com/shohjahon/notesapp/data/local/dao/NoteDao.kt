package com.shohjahon.notesapp.data.local.dao

import androidx.room.*
import com.shohjahon.notesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Query("SELECT * FROM note WHERE time = :time")
    fun getByTime(time: Long): Flow<NoteEntity>

    @Query("SELECT * FROM note ORDER BY time DESC")
    fun getAll(): Flow<List<NoteEntity>>

    @Query("DELETE FROM note WHERE time = :time")
    suspend fun delete(time: Long)

    @Query("SELECT COUNT(*) FROM note")
    fun getSize(): Flow<Int>
}