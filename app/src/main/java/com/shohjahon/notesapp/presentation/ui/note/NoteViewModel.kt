package com.shohjahon.notesapp.presentation.ui.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shohjahon.notesapp.domain.model.note.Note
import com.shohjahon.notesapp.domain.use_cases.note.CreateNoteUseCase
import com.shohjahon.notesapp.domain.use_cases.note.DeleteNoteUseCase
import com.shohjahon.notesapp.domain.use_cases.note.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val createNoteUseCase: CreateNoteUseCase, private val getNoteUseCase: GetNoteUseCase, private val deleteNoteUseCase: DeleteNoteUseCase): ViewModel() {

    private val _note: MutableStateFlow<Note?> = MutableStateFlow(null)
    val note = _note.asStateFlow()

    fun getByTime(time: Long) = viewModelScope.launch {
        getNoteUseCase.invoke(time).collectLatest {
            _note.value = it
        }
    }

    fun create(note: Note) = viewModelScope.launch {
        createNoteUseCase.invoke(note)
    }

    fun delete(time: Long) = viewModelScope.launch {
       deleteNoteUseCase.invoke(time)
    }
}