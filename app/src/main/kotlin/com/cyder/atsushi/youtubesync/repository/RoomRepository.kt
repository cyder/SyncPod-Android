package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Room
import io.reactivex.Single

/**
 * Created by chigichan24 on 2018/02/22.
 */
interface RoomRepository {
    fun createNewRoom(name: String, description: String): Single<Room>
    fun fetchJoinedRooms(): Single<List<Room>>
    fun fetch(id: Int): Single<Room?>?
}