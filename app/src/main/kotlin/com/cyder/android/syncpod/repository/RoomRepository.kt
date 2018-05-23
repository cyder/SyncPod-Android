package com.cyder.android.syncpod.repository

import android.content.res.Resources
import com.cyder.android.syncpod.model.PublishingSettingItem
import com.cyder.android.syncpod.model.Room
import com.cyder.android.syncpod.model.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by chigichan24 on 2018/02/22.
 */
interface RoomRepository {
    val isEntered: Flowable<Boolean>
    fun getPublishingSettingItems(resources: Resources): List<PublishingSettingItem>
    fun createNewRoom(name: String, description: String, isPublic: Boolean): Single<Room>
    fun fetchJoinedRooms(): Single<List<Room>>
    fun fetchPopularRooms(): Single<List<Room>>
    fun fetch(roomKey: String): Single<Room>
    fun joinRoom(roomKey: String): Completable
    fun exitRoom(): Completable
    fun exitForce(user: User)
    fun receiveForceExit(): Flowable<Unit>
}