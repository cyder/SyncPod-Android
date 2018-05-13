package com.cyder.android.syncpod.model


/**
 * Created by chigichan24 on 2018/03/21.
 */
data class Room(
        val id: Int,
        val name: String,
        val description: String,
        val key: String,
        val isPublic: Boolean,
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

    override fun equals(other: Any?): Boolean {
        val room = other as Room
        if (this.id != room.id) {
            return false
        }
        if (this.onlineUsers != room.onlineUsers) {
            return false
        }
        if (this.hasThumbnail != room.hasThumbnail) {
            return false
        }
        if (this.thumbnailUrl != room.thumbnailUrl) {
            return false
        }
        if (this.isPlaying != room.isPlaying) {
            return false
        }
        return true
    }
}