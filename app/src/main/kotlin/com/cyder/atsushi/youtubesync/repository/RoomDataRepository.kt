package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.api.request.CreateRoom
import com.cyder.atsushi.youtubesync.model.Room
import com.cyder.atsushi.youtubesync.model.User
import com.cyder.atsushi.youtubesync.util.NotFilledFormsException
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import com.hosopy.actioncable.Consumer
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/02/22.
 */
class RoomDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi,
        private val syncPodWsApi: SyncPodWsApi,
        private val token: String
) : RoomRepository {
    override val isEntered = syncPodWsApi.isEntered


    override fun joinRoom(roomKey: String): Completable {
        return syncPodWsApi.enterRoom(roomKey)
    }

    override fun exitRoom(): Completable {
        return syncPodWsApi.exitRoom()
    }

    override fun createNewRoom(name: String, description: String): Single<Room> {
        return createNewRoomValidation(name, description)
                .andThen(syncPodApi.createNewRoom(token, CreateRoom(name, description)))
                .map { it.room }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetch(roomKey: String): Single<Room> {
        return syncPodApi.getRoom(token, roomKey)
                .map { it.room }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchJoinedRooms(): Single<List<Room>> {
        return syncPodApi.getEnteredRooms(token)
                .map { it.joinedRooms }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createNewRoomValidation(name: String, description: String): Completable {
        return Completable.create { emitter ->
            if (name.isBlank() || description.isBlank()) {
                emitter.onError(NotFilledFormsException())
            } else {
                emitter.onComplete()
            }
        }
    }
}