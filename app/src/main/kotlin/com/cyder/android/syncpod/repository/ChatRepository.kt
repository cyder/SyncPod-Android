package com.cyder.android.syncpod.repository

import com.cyder.android.syncpod.model.Chat
import io.reactivex.Flowable


/**
 * Created by chigichan24 on 2018/04/19.
 */

interface ChatRepository {
    val observeChat: Flowable<Chat>
    val observePastChat: Flowable<List<Chat>>
    fun getPastChats()
    fun sendChat(message: String)
}