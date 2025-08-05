package com.example.trustline.utils

import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeFormatExt {
    private const val FORMAT = "%02d:%02d"
    fun Long.timeFormat(): String = String.format(
        FORMAT,
//        TimeUnit.MILLISECONDS.toHours(this), add this line if you need the hours field within the code
        TimeUnit.MILLISECONDS.toMinutes(this) % 60,
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )
}

fun String.toTitleCase(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}