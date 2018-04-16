package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Video
import com.google.android.youtube.player.YouTubePlayer
import io.reactivex.Flowable


/**
 * Created by chigichan24 on 2018/04/15.
 */
interface VideoRepository {
    val playerState: Flowable<YouTubePlayer.PlayerStateChangeListener>
    fun getNowPlayingVideo(): Flowable<Video>
}