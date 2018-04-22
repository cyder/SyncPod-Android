package com.cyder.atsushi.youtubesync.model


/**
 * Created by chigichan24 on 2018/04/15.
 */
data class Chat(
        val id: Int,
        val room_id: Int,
        val chat_type: String,
        val message: String,
        val created_at: String,
        val user: User?
)