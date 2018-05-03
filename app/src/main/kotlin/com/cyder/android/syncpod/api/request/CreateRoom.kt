package com.cyder.android.syncpod.api.request

/**
 * Created by chigichan24 on 2018/03/31.
 */

class CreateRoom(name: String, description: String, isPublic: Boolean) {
    private val room = Room(name, description, isPublic)
}

private data class Room (
        val name: String,
        val description: String,
        val public: Boolean
)