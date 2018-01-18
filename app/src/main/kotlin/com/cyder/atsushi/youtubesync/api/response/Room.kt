package com.cyder.atsushi.youtubesync.api.response

/**
 * Created by chigichan24 on 2018/01/18.
 */

data class Room(
        val id: Int?,
        val name: String?,
        val description: String?,
        val key: String?,
        val nowPlayingVideo: Video?,
        val lastPlatedVideo: Video?,
        val onlineUsers: List<User>?
)