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
        return syncPodApi.signIn(email, password)
                .doOnSuccess { response ->
                    sharedPreferences.edit {
                        putString(STATE_USER_TOKEN, response.user?.accessToken)
                    }
                }.subscribeOn(Schedulers.computation())
                .toCompletable()
    }

    override fun getAccessToken(): Single<String> {
        val token = sharedPreferences.getString(STATE_USER_TOKEN, null)
        return Single.create { emitter ->
            try {
                token?.run {
                    emitter.onSuccess(token)
                }
            } catch (exception: Exception) {
                emitter.onError(exception)
            }
        }
    }

    companion object {
        const val STATE_USER_TOKEN = "userToken"
    }
}

