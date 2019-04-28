package com.schibsted.core

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMATS = "yyyy-MM-dd"

fun Date.toFormattedDate(): String {
    return SimpleDateFormat(DATE_FORMATS, Locale.ENGLISH).format(this)
}

fun String.toFloatDate(): Float? {
    return try {
        SimpleDateFormat(DATE_FORMATS, Locale.ENGLISH).parse(this).time.toFloat()
    } catch (e: NumberFormatException) {
        null
    }
}

fun Float.toStringDate(): String {
    return SimpleDateFormat(DATE_FORMATS, Locale.ENGLISH).format(Date(this.toLong()))
}