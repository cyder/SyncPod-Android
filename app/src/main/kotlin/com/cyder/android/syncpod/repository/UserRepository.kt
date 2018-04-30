package com.cyder.android.syncpod.repository

import android.support.annotation.CheckResult
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by chigichan24 on 2018/01/18.
 */
interface UserRepository {
    //TODO implement val user
    @CheckResult
    fun signIn(email: String, password: String, isAgreeTerms: Boolean): Completable
    @CheckResult
    fun signUp(email: String, name: String, password:String, passwordConfirm: String, isAgreeTerms: Boolean): Completable
    fun getAccessToken(): Single<String>
    fun getMyselfId(): Single<Int>
}