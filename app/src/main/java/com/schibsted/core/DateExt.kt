package com.schibsted.core

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedDate(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(this)
}