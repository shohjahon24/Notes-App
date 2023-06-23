package com.shohjahon.notesapp.domain.use_cases.note

import com.shohjahon.notesapp.domain.model.note.Note
import com.shohjahon.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(time: Long): Flow<Note> =
         repository.getNoteByTime(time)
}