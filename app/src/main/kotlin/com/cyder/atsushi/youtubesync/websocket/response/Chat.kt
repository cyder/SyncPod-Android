package com.cyder.atsushi.youtubesync.websocket.response

import com.cyder.atsushi.youtubesync.model.User


/**
 * Created by chigichan24 on 2018/04/26.
 */

data class Chat(
        val id: Int,
        val room_id: Int,
        val chat_type: String,
        val message: String,
        val created_at: String,
        val user: User?
)