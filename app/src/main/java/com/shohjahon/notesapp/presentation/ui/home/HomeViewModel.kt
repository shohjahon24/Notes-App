package com.shohjahon.notesapp.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shohjahon.notesapp.domain.model.home.HomeNote
import com.shohjahon.notesapp.domain.use_cases.note.DeleteNoteUseCase
import com.shohjahon.notesapp.domain.use_cases.note.GetAllNotesUseCase
import com.shohjahon.notesapp.domain.use_cases.note.SizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getAllNotesUseCase: GetAllNotesUseCase, private val deleteNoteUseCase: DeleteNoteUseCase): ViewModel() {

    private val _notes: MutableStateFlow<List<HomeNote>> = MutableStateFlow(listOf())
    private val _searchData: MutableStateFlow<List<HomeNote>> = MutableStateFlow(listOf())
    val notes = _notes.asStateFlow()
    val searchData = _searchData.asStateFlow()


    fun getAllNotes() = viewModelScope.launch {
        getAllNotesUseCase.invoke().collectLatest {
            _notes.value = it
        }
    }

    fun remove(time: Long) = viewModelScope.launch {
        deleteNoteUseCase.invoke(time)
    }

    fun search(q: String) {
        _searchData.value = _notes.value.filter { q in it.title }
    }

}