package com.cyder.atsushi.youtubesync.api.mapper

import com.cyder.atsushi.youtubesync.model.Room
import com.cyder.atsushi.youtubesync.api.response.Room as RoomResponse

/**
 * Created by chigichan24 on 2018/03/21.
 */

fun List<RoomResponse>.toModel(): List<Room> =
    this.map {
        Room(
                it.id,
                it.name,
                it.description,
                it.key,
                it.nowPlayingVideo,
                it.lastPlayedVideo,
                it.onlineUsers
        )
    }

fun RoomResponse.toModel(): Room =
        Room(   this.id,
                this.name,
                this.description,
                this.key,
                this.nowPlayingVideo,
                this.lastPlayedVideo,
                this.onlineUsers)