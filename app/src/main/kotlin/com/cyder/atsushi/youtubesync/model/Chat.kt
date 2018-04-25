package com.cyder.atsushi.youtubesync.model

import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by chigichan24 on 2018/04/15.
 */
data class Chat(
        val id: Int,
        val room_id: Int,
        val chat_type: String,
        val message: String,
        val created_at: String,
        val user: User?
) {
    val isUser: Boolean
        get() = chat_type == "user"
}

fun String.formatTime():String {
    val current = Calendar.getInstance()

    val sdFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    sdFormat.timeZone = TimeZone.getTimeZone("Greenwich")
    val time = Calendar.getInstance()
    try {
        time.time = sdFormat.parse(this)
    } catch (e: ParseException) {
        System.out.println(e)
    }


    sdFormat.timeZone = current.timeZone

    if (current.get(Calendar.YEAR) === time.get(Calendar.YEAR)) {
        if (current.get(Calendar.DATE) === time.get(Calendar.DATE)) {
            sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "Hm"))
        } else {
            sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "MdHm"))
        }
    } else {
        sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "yMdHm"))
    }

    return sdFormat.format(time.time)
}