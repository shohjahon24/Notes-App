package com.shohjahon.notesapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.shohjahon.notesapp.domain.model.note.Image
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

fun <T> objectToJson(data: T): String {
    return Gson().toJson(data)
}

inline fun <reified T> jsonToObject(data: String?): T {
    return Gson().fromJson(data, T::class.java)
}

inline fun <reified T> jsonToList(data: String?): T {
    return Gson().fromJson(data, T::class.java)
}

@TypeConverter
fun listToJson(value: List<Image>): String = Gson().toJson(value)

@TypeConverter
fun jsonToList(value: String): List<Image> =
    Gson().fromJson(value, Array<Image>::class.java).toList()

fun deleteFile(filesDir: String, name: String) {
    try {
        val file = File(filesDir, name)
        if (file.exists())
            file.delete()
    } catch (_: FileNotFoundException) {

    }
}

fun deleteFolder(filesDir: String) {
    val folder = File(filesDir)

    if (folder.exists() && folder.isDirectory) {
        val deleted = folder.deleteRecursively()

        if (deleted) {
            // Folder deleted successfully
        } else {
            // Failed to delete folder
        }
    } else {
        // Folder doesn't exist or is not a directory
    }
}

fun writeBitmapToStorage(
    filesDir: String,
    name: String,
    bitmap: Bitmap,
) {
        val imageFile = File(filesDir, name)
        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (_: Exception) {
        }
}

fun readBitmapFromStorage(_path: String, name: String): Bitmap? {
    var bitmap: Bitmap? = null
    val f = File(_path, name)
    val options = BitmapFactory.Options()
    options.inPreferredConfig = Bitmap.Config.ARGB_8888
    try {
        bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    return bitmap
}