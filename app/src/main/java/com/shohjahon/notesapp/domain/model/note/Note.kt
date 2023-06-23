package com.shohjahon.notesapp.domain.model.note

data class Note(
    val title: String,
    val time: Long,
    val text: String,
    val images: List<Image>
)