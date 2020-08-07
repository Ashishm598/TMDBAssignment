package com.TorrMonk.extension

import android.util.Log
import com.assignment.tataaig.app.MyApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun String.log(initial: String? = null, tag: String? = MyApplication.LOG_TAG) {
    Log.d(tag, (if (initial != null) "$initial: " else "") + this)
}

fun String.logResponseCode(initial: String? = null, tag: String? = "code") {
    Log.d(tag, (if (initial != null) "$initial: " else "") + this)
}

fun Any.log(initial: String? = null, tag: String? = MyApplication.LOG_TAG) {
    Log.d(tag, (if (initial != null) "$initial: " else "") + this.toString())
}

fun String.removeEmptySpace(): String {
    return this.replace("\\s".toRegex(), "")
}

fun String.toListOfStrings(): List<String>? {
    var list = emptyList<String>()
    try {
        list = Gson().fromJson(this, object : TypeToken<List<String>?>() {}.type)
    } catch (e: Exception) {
        e.log("Exception occur while converting string to listOfString")
    }
    return list
}
