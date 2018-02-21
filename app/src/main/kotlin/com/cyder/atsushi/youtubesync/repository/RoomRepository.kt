package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.response.Room
import io.reactivex.Single

/**
 * Created by chigichan24 on 2018/02/22.
 */
interface RoomRepository {
    fun fetchJoinedRooms(token: String): Single<List<Room>>?
    fun fetch(id: Int, token: String): Single<Room>?
}