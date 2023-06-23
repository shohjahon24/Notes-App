package com.shohjahon.notesapp.domain.use_cases.note

import com.shohjahon.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SizeUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<Int> =
        repository.getSize()
}