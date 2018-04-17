package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.BuildConfig
import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.api.request.CreateRoom
import com.cyder.atsushi.youtubesync.model.Room
import com.cyder.atsushi.youtubesync.util.CannotJoinRoomException
import com.cyder.atsushi.youtubesync.util.NotFilledFormsException
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
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
        private val consumer: Consumer,
        private val token: String
) : RoomRepository {
    private lateinit var subscription: Subscription
    override fun joinRoom(roomKey: String): Completable {
        val channel = Channel(CHANNEL_NAME, mapOf(ROOM_KEY to roomKey))
        consumer.disconnect()
        subscription = consumer.subscriptions.create(channel)
        consumer.connect()
        return Completable.create { emitter ->
            subscription.onConnected = {
                emitter.onComplete()
            }
            subscription.onRejected = {
                emitter.onError(CannotJoinRoomException())
                consumer.disconnect()
            }
            subscription.onFailed = {
                emitter.onError(it)
                consumer.disconnect()
            }
        }
    }

    override fun exitRoom(): Completable {
        consumer.subscriptions.remove(subscription)
        consumer.disconnect()
        return Completable.complete()
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

    override fun getSubscription(): Single<Subscription> {
        return Single.just(subscription)
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
        const val CHANNEL_NAME = "RoomChannel"
        const val ROOM_KEY = "room_key"
    }
}