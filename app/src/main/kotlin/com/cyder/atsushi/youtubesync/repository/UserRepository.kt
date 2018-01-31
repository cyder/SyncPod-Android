package com.cyder.atsushi.youtubesync.repository

import android.support.annotation.CheckResult
import com.cyder.atsushi.youtubesync.api.response.User
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by chigichan24 on 2018/01/18.
 */
interface UserRepository {
    val user: Flowable<User?>
    @CheckResult
    fun signIn(userName: String, password:String): Completable
}