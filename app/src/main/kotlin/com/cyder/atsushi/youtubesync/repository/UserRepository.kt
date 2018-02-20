package com.cyder.atsushi.youtubesync.repository

import android.support.annotation.CheckResult
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by chigichan24 on 2018/01/18.
 */
interface UserRepository {
    //TODO implement val user
    @CheckResult
    fun signIn(email: String, password:String): Completable
    fun getAccessToken(): Single<String>?
}