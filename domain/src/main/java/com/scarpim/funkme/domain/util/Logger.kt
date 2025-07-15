package com.scarpim.funkme.domain.util

import android.util.Log
import java.util.logging.Logger

// TODO - avoid logs in release
inline fun logD(message: () -> String) {
    val tag = getCallerTag()
    Log.d(tag, message())
}

inline fun logE(message: () -> String) {
    val tag = getCallerTag()
    Log.e(tag, message())
}

inline fun logI(message: () -> String) {
    val tag = getCallerTag()
    Log.i(tag, message())
}

inline fun logW(message: () -> String) {
    val tag = getCallerTag()
    Log.w(tag, message())
}

fun getCallerTag(): String {
    val stackTrace = Throwable().stackTrace

    // Skip frames until we find a class that is NOT the logger itself
    for (element in stackTrace) {
        val className = element.className
        if (!className.contains("LoggerKt")) { // or whatever your logger file is named
            val simpleName = className.substringAfterLast('.')
            return if (simpleName.length > 23) simpleName.take(23) else simpleName
        }
    }

    return "Unknown"
}