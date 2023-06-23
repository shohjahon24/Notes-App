package com.shohjahon.notesapp.domain.model.note

import android.graphics.Bitmap

data class Image(
    val name: String,
    var bitmap: Bitmap?
)