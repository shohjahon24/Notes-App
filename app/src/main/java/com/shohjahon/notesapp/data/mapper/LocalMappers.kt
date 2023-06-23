package com.shohjahon.notesapp.data.mapper

import com.shohjahon.notesapp.data.local.entity.NoteEntity
import com.shohjahon.notesapp.domain.model.home.HomeNote
import com.shohjahon.notesapp.domain.model.note.Note
import com.shohjahon.notesapp.util.jsonToList
import com.shohjahon.notesapp.util.listToJson

fun NoteEntity.toNote() = Note(
    time = time,
    images = jsonToList(images),
    title = title,
    text = text
)


fun Note.toEntity() = NoteEntity(
    time = time,
    images = listToJson(images),
    title = title,
    text = text
)

fun NoteEntity.toHomeNote(): HomeNote {
    val images = jsonToList(images)
    return HomeNote(
    time = time,
    img = if (images.isEmpty()) "" else images[0].name,
    title = title,
    description = text
)}

fun List<NoteEntity>.toHomeNoteList(): List<HomeNote> {
    return this.map { note -> note.toHomeNote() }
}



