package com.cyder.atsushi.youtubesync.api.request

/**
 * Created by chigichan24 on 2018/03/31.
 */

class CreateRoom(name: String, description: String) {
    private val room = Room(name, description)
}

private data class Room (
        val name: String,
        val description: String
)