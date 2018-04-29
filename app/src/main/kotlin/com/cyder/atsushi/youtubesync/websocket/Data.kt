package com.cyder.atsushi.youtubesync.websocket

import com.cyder.atsushi.youtubesync.api.response.Video
import com.cyder.atsushi.youtubesync.websocket.response.Chat
import com.google.gson.annotations.SerializedName


/**
 * Created by chigichan24 on 2018/04/15.
 */

data class Data(
        val video: Video,
        val chat: Chat,
        val message: String,
        @SerializedName("play_list") val playList: List<Video>,
        @SerializedName("past_chats") val pastChat: List<Chat>
)