package com.cyder.android.syncpod.repository

import com.cyder.android.syncpod.api.SyncPodApi
import com.cyder.android.syncpod.api.mapper.toModel
import com.cyder.android.syncpod.api.request.CreateRoom
import com.cyder.android.syncpod.model.Room
import com.cyder.android.syncpod.model.User
import com.cyder.android.syncpod.util.NotFilledFormsException
import com.cyder.android.syncpod.websocket.SyncPodWsApi
import io.reactivex.Completable
import io.reactivex.Flowable
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

    override fun createNewRoom(name: String, description: String, isPublic: Boolean): Single<Room> {
        return createNewRoomValidation(name, description)
                .andThen(syncPodApi.createNewRoom(token, CreateRoom(name, description, isPublic)))
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

    override fun exitForce(user: User) {
        syncPodWsApi.exitForce(user)
    }

    override fun receiveForceExit(): Flowable<Unit> {
        return syncPodWsApi.errorResponse
                .filter { it.data?.message == FORCE_EXIT }
                .map { INVOCATION }
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

    companion object {
        const val FORCE_EXIT: String = "force exit"
        val INVOCATION = Unit
    }
}