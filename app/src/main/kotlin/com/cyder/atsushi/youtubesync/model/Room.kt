package com.cyder.atsushi.youtubesync.model

import com.cyder.atsushi.youtubesync.api.response.User
import com.cyder.atsushi.youtubesync.api.response.Video

/**
 * Created by chigichan24 on 2018/03/21.
 */
data class Room(
        val id: Int,
        val name: String,
        val description: String,
        val key: String,
        val nowPlayingVideo: Video?,
        val lastPlayedVideo: Video?,
        val onlineUsers: List<User>?
){
    val hasThumbnail: Boolean
            get() = nowPlayingVideo is Video || lastPlayedVideo is Video

    val thumbnailUrl: String?
            get() {
                if ( nowPlayingVideo is Video ) {
                    return nowPlayingVideo.thumbnailUrl
                }
                if ( lastPlayedVideo is Video ) {
                    return lastPlayedVideo.thumbnailUrl
                }
                return null
            }
    val thumbnailTitle: String?
            get() {
                if ( nowPlayingVideo is Video ) {
                    return nowPlayingVideo.title
                }
                if ( lastPlayedVideo is Video ) {
                    return lastPlayedVideo.title
                }
                return null
            }
    val isPlaying: Boolean
            get() = nowPlayingVideo is Video
}