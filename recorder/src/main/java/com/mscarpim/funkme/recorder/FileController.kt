package com.mscarpim.funkme.recorder

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Class to control files saving and retrieving.
 *
 * TODO - check for better place to save recordings and localize naming
 */
class FileController @Inject constructor(@ApplicationContext private val context: Context) {

    fun createFileWithDateName(): File {
        val timeStamp = SimpleDateFormat("HHmmss_yyyyMMdd", Locale.getDefault()).format(Date())
        return File(context.externalCacheDir?.absolutePath, "record-$timeStamp.wav")
    }

    fun getFilesSaved(): List<File> =
        context.externalCacheDir?.listFiles()?.toList() ?: emptyList()
}