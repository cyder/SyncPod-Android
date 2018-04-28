package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Chat
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import com.cyder.atsushi.youtubesync.websocket.mapper.toModel
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
    private var chatStream: Subject<Chat> = BehaviorSubject.create()
    private var pastChatStream: Subject<List<Chat>> = BehaviorSubject.createDefault(emptyList())

    init {
        initSubjects()
    }

    private fun startObserve() {
        syncPodWsApi.chatResponse
                .subscribe {
                    it.data?.apply {
                        this@ChatDataRepository.chatStream.onNext(this.chat.toModel())
                    }
                }
        syncPodWsApi.pastChatsResponse
                .subscribe {
                    it.data?.apply {
                        this@ChatDataRepository.pastChatStream.onNext(this.pastChat.toModel())
                    }
                }
    }

    private fun initSubjects() {
        syncPodWsApi.isEntered
                .filter { it }
                .subscribe {
                    chatStream = BehaviorSubject.create()
                    pastChatStream = BehaviorSubject.createDefault(emptyList())
                    startObserve()
                }
        syncPodWsApi.isEntered
                .filter { !it }
                .subscribe {
                    chatStream.onComplete()
                    pastChatStream.onComplete()
                }
    }

    override val observeChat: Flowable<Chat>
        get() = chatStream.toFlowable(BackpressureStrategy.LATEST)

    override fun getPastChats(): Flowable<List<Chat>> {
        syncPodWsApi.requestPastChat()
        return pastChatStream.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun sendChat(message: String) {
        message.takeUnless { it.isBlank() }?.apply {
            syncPodWsApi.sendMessage(this)
        }
    }
}