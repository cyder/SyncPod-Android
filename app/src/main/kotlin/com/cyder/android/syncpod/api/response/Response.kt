package com.cyder.android.syncpod.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by chigichan24 on 2018/01/18.
 */

data class Response(
        val result: String,
        val user: User?,
        val room: Room?,
        @SerializedName("joined_rooms") val joinedRooms: List<Room>?
)