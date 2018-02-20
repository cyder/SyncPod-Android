package com.cyder.atsushi.youtubesync.repository

import android.content.SharedPreferences
import androidx.content.edit
import com.cyder.atsushi.youtubesync.api.SyncPodApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/17.
 */
class UserDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi,
        private val sharedPreferences: SharedPreferences
) : UserRepository {

    override fun signIn(email: String, password: String): Completable {
        val result = syncPodApi.signIn(email, password)
        result.subscribe( {
            sharedPreferences.edit {
                putString(STATE_USER_TOKEN, result.blockingGet().user?.accessToken)
            }
        },{})
        return result.subscribeOn(Schedulers.computation())
                .toCompletable()
    }

    override fun getAccessToken(): Single<String>? {
        return Single
                .just(sharedPreferences.getString(STATE_USER_TOKEN, "fail"))
                .filter { it != "fail" }
                .toSingle()
    }

    companion object {
        const val STATE_USER_TOKEN = "userToken"
    }
}

