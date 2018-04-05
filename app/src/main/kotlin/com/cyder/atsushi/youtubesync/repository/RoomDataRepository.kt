package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.api.request.CreateRoom
import com.cyder.atsushi.youtubesync.model.Room
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URI
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/02/22.
 */
class RoomDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi,
        private val token: String
) : RoomRepository {
    override fun joinRoom(roomKey: String): Completable {
        CableConnection.provideConsumer(token)
        CableConnection.provideSubscription(roomKey)

        CableConnection.provideConsumer().connect()

        return Completable.create { emitter ->
            CableConnection.provideSubscription().onConnected = {
                emitter.onComplete()
            }
            CableConnection.provideSubscription().onRejected = {
                emitter.onError(CannotJoinRoomException())
            }
            CableConnection.provideSubscription().onFailed = {
                emitter.onError(it)
            }
        }
    }

    override fun createNewRoom(name: String, description: String): Single<Room> {
        return syncPodApi.createNewRoom(token, CreateRoom(name, description))
                .map{ it.room }
                .map{ it.toModel() }
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
        const val WS_URL = "ws://59.106.220.89:3000/cable/"
        const val CHANNEL_NAME = "RoomChannel"
        const val ROOM_KEY = "room_key"
        const val TOKEN = "token"
    }

    object CableConnection {
        private var consumer: Consumer? = null
        private var subscription: Subscription? = null
        fun provideConsumer(token: String? = null): Consumer {
            if(consumer == null && token != null) {
                val options = Consumer.Options()
                options.connection.query = mapOf(TOKEN to token)
                consumer = ActionCable.createConsumer(URI(WS_URL),options)
            }
            return consumer as Consumer
        }

        fun provideSubscription(roomKey: String? = null): Subscription{
            if(subscription == null && roomKey != null) {
                val channel = Channel(CHANNEL_NAME, mapOf(ROOM_KEY to roomKey))
                subscription = consumer?.subscriptions?.create(channel)
            }
            return subscription as Subscription
        }
    }

    internal class CannotJoinRoomException : Exception("can not join room because of banned or mistook key")
}