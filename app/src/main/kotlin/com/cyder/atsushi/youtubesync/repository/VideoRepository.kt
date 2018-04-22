package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Video
import com.google.android.youtube.player.YouTubePlayer
import io.reactivex.Flowable


/**
 * Created by chigichan24 on 2018/04/15.
 */
interface VideoRepository {
    val developerKey: Flowable<String>
    val playerState: Flowable<YouTubePlayer.PlayerStateChangeListener>
    fun observeIsPlaying(): Flowable<Boolean>
    fun observePrepareVideo(): Flowable<Video>
    fun observeNowPlayingVideo(): Flowable<Video>
    fun getNowPlayingVideo(): Flowable<Video>
    fun getPlayList(): Flowable<List<Video>>
}