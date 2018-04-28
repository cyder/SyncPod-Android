package com.cyder.atsushi.youtubesync.websocket.response

import com.cyder.atsushi.youtubesync.model.User
import com.google.gson.annotations.SerializedName


/**
 * Created by chigichan24 on 2018/04/26.
 */

data class Chat(
        val id: Int,
        @SerializedName("room_id") val roomId: Int,
        @SerializedName("chat_type") val type: String,
        val message: String,
        @SerializedName("created_at") val time: String,
        val user: User?
)