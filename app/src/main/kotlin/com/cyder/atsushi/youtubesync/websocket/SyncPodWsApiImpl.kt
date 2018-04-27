package com.cyder.atsushi.youtubesync.websocket

import com.cyder.atsushi.youtubesync.model.User
import com.cyder.atsushi.youtubesync.util.CannotJoinRoomException
import com.google.gson.GsonBuilder
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/22.
 */
class SyncPodWsApiImpl @Inject constructor(
        private val consumer: Consumer
) : SyncPodWsApi {
    private var subscription: Subscription? = null

    private var nowPlayingEvent: Subject<Response> = BehaviorSubject.create()
    private var startVideoEvent: Subject<Response> = BehaviorSubject.create()
    private var playListEvent: Subject<Response> = BehaviorSubject.create()
    private var addVideoEvent: Subject<Response> = BehaviorSubject.create()
    private var chatEvent: Subject<Response> = BehaviorSubject.create()
    private val manageEnteredStream: Subject<Boolean> = BehaviorSubject.createDefault(false)
    private var pastChatsEvent: Subject<Response> = BehaviorSubject.create()
    private var errorEvent: Subject<Response> = BehaviorSubject.create()
    override val nowPlayingResponse: Flowable<Response>
        get() = nowPlayingEvent.toFlowable(BackpressureStrategy.LATEST)
    override val startVideoResponse: Flowable<Response>
        get() = startVideoEvent.toFlowable(BackpressureStrategy.LATEST)
    override val playListResponse: Flowable<Response>
        get() = playListEvent.toFlowable(BackpressureStrategy.LATEST)
    override val addVideoResponse: Flowable<Response>
        get() = addVideoEvent.toFlowable(BackpressureStrategy.LATEST)
    override val chatResponse: Flowable<Response>
        get() = chatEvent.toFlowable(BackpressureStrategy.LATEST)
    override val pastChatsResponse: Flowable<Response>
        get() = pastChatsEvent.toFlowable(BackpressureStrategy.LATEST)
    override val errorResponse: Flowable<Response>
        get() = errorEvent.toFlowable(BackpressureStrategy.LATEST)

    override val isEntered: Flowable<Boolean> = manageEnteredStream.toFlowable(BackpressureStrategy.LATEST)

    override fun enterRoom(roomKey: String): Completable {

        nowPlayingEvent = BehaviorSubject.create()
        startVideoEvent = BehaviorSubject.create()
        playListEvent = BehaviorSubject.create()
        addVideoEvent = BehaviorSubject.create()
        chatEvent = BehaviorSubject.create()
        pastChatsEvent = BehaviorSubject.create()
        errorEvent = BehaviorSubject.create()

        val channel = Channel(CHANNEL_NAME, mapOf(ROOM_KEY to roomKey))
        consumer.disconnect()
        subscription = consumer.subscriptions.create(channel)
        consumer.connect()
        return Completable.create { emitter ->
            subscription?.onConnected = {
                emitter.onComplete()
                manageEnteredStream.onNext(true)
                startRouting()
            }
            subscription?.onRejected = {
                emitter.onError(CannotJoinRoomException())
                consumer.disconnect()
            }
            subscription?.onFailed = {
                emitter.onError(it)
                consumer.disconnect()
            }
        }
    }

    override fun exitRoom(): Completable {
        subscription?.apply {
            consumer.subscriptions.remove(this)
        }
        consumer.disconnect()
        nowPlayingEvent.onComplete()
        startVideoEvent.onComplete()
        playListEvent.onComplete()
        addVideoEvent.onComplete()
        chatEvent.onComplete()
        pastChatsEvent.onComplete()
        errorEvent.onComplete()
        manageEnteredStream.onNext(false)
        return Completable.complete()
    }

    override fun exitForce(user: User) {
        subscription?.perform(EXIT_FORCE, mapOf(USER_ID to user.id))
    }

    override fun requestNowPlayingVideo() {
        subscription?.perform(NOW_PLAYING)
    }

    override fun requestPlayList() {
        subscription?.perform(PLAY_LIST)
    }

    override fun requestAddVideo(videoId: String) {
        subscription?.perform(ADD_VIDEO, mapOf(VIDEO_ID to videoId))
    }

    private fun startRouting() {
        subscription?.onReceived = {
            if (it is String) {
                val response = it.toResponse()
                when (response.dataType) {
                    NOW_PLAYING, START_VIDEO -> {
                        nowPlayingEvent.onNext(response)
                    }
                    CHAT -> {
                        chatEvent.onNext(response)
                    }
                    PAST_CHATS -> {
                        pastChatsEvent.onNext(response)
                    }
                    PLAY_LIST -> {
                        playListEvent.onNext(response)
                    }
                    ADD_VIDEO -> {
                        addVideoEvent.onNext(response)
                    }
                    ERROR -> {
                        errorEvent.onNext(response)
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
        const val CHANNEL_NAME = "RoomChannel"
        const val ROOM_KEY = "room_key"
        const val EXIT_FORCE = "exit_force"
        const val USER_ID = "user_id"
        const val PAST_CHATS = "past_chats"
        const val VIDEO_ID = "youtube_video_id"
        const val ERROR = "error"
    }

}