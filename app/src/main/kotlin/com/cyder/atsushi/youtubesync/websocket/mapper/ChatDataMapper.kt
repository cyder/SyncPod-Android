package com.cyder.atsushi.youtubesync.websocket.mapper

import com.cyder.atsushi.youtubesync.model.Chat
import com.cyder.atsushi.youtubesync.model.formatTime
import com.cyder.atsushi.youtubesync.websocket.response.Chat as ChatResponse


/**
 * Created by chigichan24 on 2018/04/26.
 */

fun List<ChatResponse>.toModel(): List<Chat> =
        this.map{
            it.toModel()
        }

fun ChatResponse.toModel(): Chat =
        Chat(
                this.id,
                this.room_id,
                this.chat_type,
                this.message,
                this.created_at.formatTime(),
                this.user)