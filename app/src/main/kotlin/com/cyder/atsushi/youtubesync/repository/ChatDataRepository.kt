package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Chat
import com.hosopy.actioncable.Consumer
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/19.
 */
class ChatDataRepository @Inject constructor(
        private val consumer: Consumer
) : ChatRepository {

    override fun emitNewChat(chat: Chat): Flowable<Chat> {
        return Observable.just(chat).toFlowable(BackpressureStrategy.LATEST)
    }


}