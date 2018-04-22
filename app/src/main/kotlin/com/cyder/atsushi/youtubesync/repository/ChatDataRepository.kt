package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Chat
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import com.hosopy.actioncable.Consumer
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/19.
 */
class ChatDataRepository @Inject constructor(
        private val consumer: Consumer,
        private val syncPodWsApi: SyncPodWsApi
) : ChatRepository {
    private val chatStream: Subject<Chat> = BehaviorSubject.create()

    init {
        syncPodWsApi.chatResponse
                .subscribe {
                    it.data?.apply {
                        this@ChatDataRepository.chatStream.onNext(this.chat)
                    }
                }
    }

    override val observeChat: Flowable<Chat> = chatStream.toFlowable(BackpressureStrategy.LATEST)



}