package com.cyder.android.syncpod.repository

import com.cyder.android.syncpod.model.Video
import com.google.android.youtube.player.YouTubePlayer
import io.reactivex.Completable
import io.reactivex.Flowable


/**
 * Created by chigichan24 on 2018/04/15.
 */
interface VideoRepository {
    val developerKey: Flowable<String>
    val playerState: Flowable<YouTubePlayer.PlayerStateChangeListener>
    val playListObservable: Flowable<List<Video>>
    fun observeIsPlaying(): Flowable<Boolean>
    fun observePrepareVideo(): Flowable<Video>
    fun observeNowPlayingVideo(): Flowable<Video>
    fun getNowPlayingVideo(): Flowable<Video>
    fun getPlayList(): Flowable<List<Video>>
    fun addPlayList(video: Video): Completable
}