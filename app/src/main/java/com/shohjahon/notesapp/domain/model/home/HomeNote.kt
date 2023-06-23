package com.shohjahon.notesapp.domain.model.home

import android.graphics.Bitmap

data class HomeNote(
    val time: Long,
    val img: String,
    val title: String,
    val description: String
)