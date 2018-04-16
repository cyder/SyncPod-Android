package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Room
import com.hosopy.actioncable.Subscription
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by chigichan24 on 2018/02/22.
 */
interface RoomRepository {
    val developerKey: Flowable<String>
    fun createNewRoom(name: String, description: String): Single<Room>
    fun getSubscription(): Single<Subscription>
    fun fetchJoinedRooms(): Single<List<Room>>
    fun fetch(id: Int): Single<Room?>?
    fun joinRoom(roomKey: String): Completable
    fun exitRoom(): Completable
}