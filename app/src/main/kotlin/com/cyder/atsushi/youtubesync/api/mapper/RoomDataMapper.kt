package com.cyder.atsushi.youtubesync.api.mapper

import com.cyder.atsushi.youtubesync.model.Room
import com.cyder.atsushi.youtubesync.api.response.Room as RoomResponse

/**
 * Created by chigichan24 on 2018/03/21.
 */

fun List<RoomResponse>.toModel(): List<Room> =
    this.map {
        it.toModel()
    }

fun RoomResponse.toModel(): Room =
        Room(   this.id,
                this.name,
                this.description,
                this.key,
                this.nowPlayingVideo?.toModel(),
                this.lastPlayedVideo?.toModel(),
                this.onlineUsers?.toModel())