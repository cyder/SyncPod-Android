package com.cyder.android.syncpod.websocket

import com.cyder.android.syncpod.api.response.Video
import com.cyder.android.syncpod.websocket.response.Chat
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