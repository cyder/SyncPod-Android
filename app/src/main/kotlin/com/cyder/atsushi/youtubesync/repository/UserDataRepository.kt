package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SignInApi
import com.cyder.atsushi.youtubesync.api.response.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/17.
 */
class UserDataRepository @Inject constructor(
        private val signInApi: SignInApi,
        private val userName: String,
        private val password: String
) : UserRepository {
    override val user: Flowable<User?> =
            signInApi.getSession(userName, password)
                    .map { it.user }
                    .toFlowable()
                    .subscribeOn(Schedulers.computation())

    override fun signIn(): Completable {
        return signInApi.getSession(userName, password)
                .subscribeOn(Schedulers.computation())
                .toCompletable()
    }
}