package com.shohjahon.notesapp.presentation.adapters

import java.text.FieldPosition

interface ItemCallBack {
    fun onClicked(time: Long)
    fun onLongClicked(time: Long, position: Int)
}