package com.cyder.atsushi.youtubesync.model

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by chigichan24 on 2018/04/15.
 */
data class Chat(
        val id: Int,
        val roomId: Int,
        val type: String,
        val message: String,
        val time: String,
        val user: User?
) {
    val isUser: Boolean
        get() = type == "user"
}

fun String.formatTime(): String {
    val current = Calendar.getInstance()

    val sdFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    sdFormat.timeZone = TimeZone.getTimeZone("Greenwich")
    val time = Calendar.getInstance()
    time.time = sdFormat.parse(this)


    sdFormat.timeZone = current.timeZone

    if (current.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
        if (current.get(Calendar.DATE) == time.get(Calendar.DATE)) {
            sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "Hm"))
        } else {
            sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "MdHm"))
        }
    } else {
        sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "yMdHm"))
    }

    return sdFormat.format(time.time)
}