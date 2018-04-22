package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Chat
import com.cyder.atsushi.youtubesync.websocket.Response
import com.google.gson.GsonBuilder
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/19.
 */
class ChatDataRepository @Inject constructor(
        private val consumer: Consumer,
        private val subscription: Subscription
) : ChatRepository {

    override fun emitNewChat(chat: Chat): Flowable<Chat> {
        return Observable.just(chat).toFlowable(BackpressureStrategy.LATEST)
    }

    init {
        startRouting()
    }

    private fun startRouting() {
        subscription.onReceived = {
            if(it is String) {
                val response = it.toResponse()
                when (response.dataType) {
                    ADD_CHAT -> {
                    }
                    PAST_CHATS -> {
                    }
                }
            }
        }
    }

    private fun String.toResponse(): Response {
        return GsonBuilder().create().fromJson(this, Response::class.java)
    }

    companion object {
        const val ADD_CHAT: String = "add_chat"
        const val PAST_CHATS: String = "past_chats"
    }

}