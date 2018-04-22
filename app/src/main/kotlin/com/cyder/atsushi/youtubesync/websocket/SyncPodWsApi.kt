package com.cyder.atsushi.youtubesync.websocket

import com.cyder.atsushi.youtubesync.model.User
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Singleton


/**
 * Created by chigichan24 on 2018/04/22.
 */
@Singleton
interface SyncPodWsApi {
    val nowPlayingResponse: Flowable<Response>
    val startVideoResponse: Flowable<Response>
    val playListResponse: Flowable<Response>
    val addVideoResponse: Flowable<Response>
    val chatResponse: Flowable<Response>
    val pastChatsResponse: Flowable<Response>
    val isEntered: Flowable<Boolean>
    fun enterRoom(roomKey: String): Completable
    fun exitRoom(): Completable
    fun exitForce(user: User)
    fun requestNowPlayingVideo()
    fun requestPlayList()
}