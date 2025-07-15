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

        // avoid creating a file that already exist in dir
        var fullName = "record-$timeStamp.wav"
        var i = 1
        while (hasFileInDir(fullName)) {
            fullName = "record-$timeStamp+i.wav"
            i++
        }

        return File(context.externalCacheDir?.absolutePath, fullName)
    }

    fun getFilesSaved(): List<File> =
        context.externalCacheDir?.listFiles()?.toList() ?: emptyList()

    private fun hasFileInDir(fileName: String): Boolean {
        val file = File(context.externalCacheDir, fileName)
        return file.exists()
    }
}