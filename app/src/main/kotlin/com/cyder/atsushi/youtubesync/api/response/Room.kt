package com.cyder.atsushi.youtubesync.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by chigichan24 on 2018/01/18.
 */

data class Room(
        val id: Int,
        val name: String,
        val description: String,
        val key: String,
        @SerializedName("now_playing_video") val nowPlayingVideo: Video?,
        @SerializedName("last_played_video") val lastPlayedVideo: Video?,
        @SerializedName("online_users") val onlineUsers: List<User>?
)