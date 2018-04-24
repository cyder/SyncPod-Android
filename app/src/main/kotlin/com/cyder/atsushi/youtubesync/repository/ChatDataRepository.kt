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
    private var chatStream: Subject<Chat> = BehaviorSubject.create()

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
    }

    private fun initSubjects() {
        syncPodWsApi.isEntered
                .filter { it }
                .subscribe {
                    chatStream = BehaviorSubject.create()
                    startObserve()
                }
        syncPodWsApi.isEntered
                .filter { !it }
                .subscribe {
                    chatStream.onComplete()
                }
    }

    override val observeChat: Flowable<Chat> = chatStream.toFlowable(BackpressureStrategy.LATEST)

}