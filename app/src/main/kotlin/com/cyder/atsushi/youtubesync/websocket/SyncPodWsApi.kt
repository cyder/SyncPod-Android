package com.cyder.atsushi.youtubesync.websocket

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
    fun perform(request: String)
}