package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SignInApi
import com.cyder.atsushi.youtubesync.api.response.User
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/17.
 */
class UserDataRepository @Inject constructor(
        private val signInApi: SignInApi
) : UserRepository {
    override override val user: Flowable<User>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun signIn(): Completable {
    }
}