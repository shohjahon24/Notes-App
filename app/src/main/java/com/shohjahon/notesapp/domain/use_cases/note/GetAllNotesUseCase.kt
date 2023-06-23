package com.shohjahon.notesapp.domain.use_cases.note

import com.shohjahon.notesapp.domain.model.home.HomeNote
import com.shohjahon.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<HomeNote>> =
        repository.getAllNotes()
}