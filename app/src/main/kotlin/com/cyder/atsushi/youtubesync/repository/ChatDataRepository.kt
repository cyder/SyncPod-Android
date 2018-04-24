package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Chat
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
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
    private lateinit var chatStream: Subject<Chat>
    private lateinit var pastChatStream: Subject<List<Chat>>

    init {
        initSubjects()
    }

    private fun startObserve() {
        syncPodWsApi.chatResponse
                .subscribe {
                    it.data?.apply {
                        this@ChatDataRepository.chatStream.onNext(this.chat)
                    }
                }
        syncPodWsApi.pastChatsResponse
                .subscribe {
                    it.data?.apply {
                        this@ChatDataRepository.pastChatStream.onNext(this.pastChat)
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

    override val observeChat: Flowable<Chat> = chatStream.toFlowable(BackpressureStrategy.LATEST)

    override fun getPastChats(): Flowable<List<Chat>> {
        syncPodWsApi.requestPastChat()
        return pastChatStream.toFlowable(BackpressureStrategy.LATEST)
    }
}