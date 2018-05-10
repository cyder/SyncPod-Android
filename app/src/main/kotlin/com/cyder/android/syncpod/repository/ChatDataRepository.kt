package com.cyder.android.syncpod.repository

import com.cyder.android.syncpod.model.Chat
import com.cyder.android.syncpod.websocket.SyncPodWsApi
import com.cyder.android.syncpod.websocket.mapper.toModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/19.
 */
class ChatDataRepository @Inject constructor(
        private val syncPodWsApi: SyncPodWsApi
) : ChatRepository {
    override val observePastChat: Flowable<List<Chat>>
        get() = syncPodWsApi.pastChatsResponse
                .map { it.data?.pastChat?.toModel() }

    override val observeChat: Flowable<Chat>
        get() = syncPodWsApi.chatResponse
                .map { it.data?.chat?.toModel() }

    override fun getPastChats() {
        syncPodWsApi.requestPastChat()
    }

    override fun sendChat(message: String) {
        message.takeUnless { it.isBlank() }?.apply {
            syncPodWsApi.sendMessage(this)
        }
    }
}