package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Chat
import io.reactivex.Flowable


/**
 * Created by chigichan24 on 2018/04/19.
 */

interface ChatRepository {
    fun emitNewChat(chat: Chat): Flowable<Chat>
}