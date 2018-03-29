package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Room
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/02/22.
 */
class RoomDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi
) : RoomRepository {
    override fun fetch(id: Int, token: String): Single<Room?>? {
        return try {
            syncPodApi.getRoom(token)
                    .map { it.joinedRooms?.last() }
                    .map { it.toModel() }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
        } catch (e: Exception) {
            null
        }
    }

    override fun fetchJoinedRooms(token: String): Single<List<Room>> {
        return syncPodApi.getRoom(token)
                .map { it.joinedRooms }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}