package com.shohjahon.notesapp.domain.use_cases.note

import com.shohjahon.notesapp.domain.model.note.Note
import com.shohjahon.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) =
        repository.createNote(note)
}