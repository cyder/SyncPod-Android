package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.api.request.CreateRoom
import com.cyder.atsushi.youtubesync.model.Room
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
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
        private val consumer: Consumer,
        private val token: String
) : RoomRepository {
    lateinit var subscription: Subscription
    override fun joinRoom(roomKey: String): Completable {
        val channel = Channel(CHANNEL_NAME, mapOf(ROOM_KEY to roomKey))
        subscription = consumer.subscriptions.create(channel)
        consumer.connect()
        return Completable.create { emitter ->
            subscription.onConnected = {
                emitter.onComplete()
            }
            subscription.onRejected = {
                emitter.onError(CannotJoinRoomException())
            }
            subscription.onFailed = {
                emitter.onError(it)
            }
        }
    }

    override fun createNewRoom(name: String, description: String): Single<Room> {
        return syncPodApi.createNewRoom(token, CreateRoom(name, description))
                .map { it.room }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetch(id: Int): Single<Room?>? {
        return try {
            syncPodApi.getEnteredRooms(token)
                    .map { it.joinedRooms?.last() }
                    .map { it.toModel() }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
        } catch (e: Exception) {
            null
        }
    }

    override fun fetchJoinedRooms(): Single<List<Room>> {
        return syncPodApi.getEnteredRooms(token)
                .map { it.joinedRooms }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        const val CHANNEL_NAME = "RoomChannel"
        const val ROOM_KEY = "room_key"
    }

    internal class CannotJoinRoomException : Exception("can not join room because of banned or mistook key")
}