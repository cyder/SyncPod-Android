package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.response.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/17.
 */
class UserDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi
) : UserRepository {
    override val user: Flowable<User?> =
            syncPodApi.signIn("debug", "debug")
                    .map { it.user }
                    .toFlowable()
                    .subscribeOn(Schedulers.computation())

    override fun signIn(email: String, password: String): Completable {
        return syncPodApi.signIn(email, password)
                .subscribeOn(Schedulers.computation())
                .toCompletable()
    }
}