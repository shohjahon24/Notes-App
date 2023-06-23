package com.shohjahon.notesapp.domain.use_cases.note

import com.shohjahon.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(time: Long) =
        repository.deleteNote(time)
}