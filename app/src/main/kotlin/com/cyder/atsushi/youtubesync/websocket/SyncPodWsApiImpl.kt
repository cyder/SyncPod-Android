package com.cyder.atsushi.youtubesync.websocket

import com.google.gson.GsonBuilder
import com.hosopy.actioncable.Subscription
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/22.
 */
class SyncPodWsApiImpl @Inject constructor(
        private val subscription: Subscription
) : SyncPodWsApi {
    private val nowPlayingEvent: Subject<Response> = BehaviorSubject.create()
    private val startVideoEvent: Subject<Response> = BehaviorSubject.create()
    private val playListEvent: Subject<Response> = BehaviorSubject.create()
    private val addVideoEvent: Subject<Response> = BehaviorSubject.create()
    private val chatEvent: Subject<Response> = BehaviorSubject.create()

    override val nowPlayingResponse: Flowable<Response> = nowPlayingEvent.toFlowable(BackpressureStrategy.LATEST)
    override val startVideoResponse: Flowable<Response> = startVideoEvent.toFlowable(BackpressureStrategy.LATEST)
    override val playListResponse: Flowable<Response> = playListEvent.toFlowable(BackpressureStrategy.LATEST)
    override val addVideoResponse: Flowable<Response> = addVideoEvent.toFlowable(BackpressureStrategy.LATEST)
    override val chatResponse: Flowable<Response> = chatEvent.toFlowable(BackpressureStrategy.LATEST)
    override fun perform(request: String) {
        subscription.perform(request)
    }

    init {
        subscription.onReceived = {
            if (it is String) {
                val response = it.toResponse()
                when (response.dataType) {
                    NOW_PLAYING, START_VIDEO -> {
                        nowPlayingEvent.onNext(response)
                        startVideoEvent.onNext(response)
                    }
                    CHAT -> {
                        chatEvent.onNext(response)
                    }
                    PLAY_LIST -> {
                        playListEvent.onNext(response)
                    }
                    ADD_VIDEO -> {
                        addVideoEvent.onNext(response)
                    }
                }
            }
        }
    }

    private fun String.toResponse(): Response {
        return GsonBuilder().create().fromJson(this, Response::class.java)
    }


    companion object {
        const val NOW_PLAYING: String = "now_playing_video"
        const val START_VIDEO: String = "start_video"
        const val CHAT: String = "add_chat"
        const val PLAY_LIST: String = "play_list"
        const val ADD_VIDEO: String = "add_video"
    }

}