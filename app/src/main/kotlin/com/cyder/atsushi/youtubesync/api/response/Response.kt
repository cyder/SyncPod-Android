package com.cyder.atsushi.youtubesync.api.response

/**
 * Created by chigichan24 on 2018/01/18.
 */

data class Response(
        val result: String,
        val user: User?,
        val room: Room?,
        val joinedRooms: List<Room>?
)