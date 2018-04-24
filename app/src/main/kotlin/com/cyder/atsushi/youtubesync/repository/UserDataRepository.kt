package com.cyder.atsushi.youtubesync.repository

import android.content.SharedPreferences
import androidx.content.edit
import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.request.SignUp
import com.cyder.atsushi.youtubesync.api.response.User
import com.cyder.atsushi.youtubesync.util.*
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

    override fun signIn(email: String, password: String, isAgreeTerms: Boolean): Completable {
        return signInValidate(email, password, isAgreeTerms)
                .andThen(syncPodApi.signIn(email, password))
                .doOnSuccess { response ->
                    response.user?.apply {
                        saveSharedPreferences(this)
                    }
                }.subscribeOn(Schedulers.computation())
                .toCompletable()
    }

    override fun signUp(email: String, name: String, password: String, passwordConfirm: String, isAgreeTerms: Boolean): Completable {
        return signUpValidate(email, name, password, passwordConfirm, isAgreeTerms)
                .andThen(syncPodApi.signUp(SignUp(email, name, password)))
                .doOnSuccess { response ->
                    response.user?.apply {
                        saveSharedPreferences(this)
                    }
                }.subscribeOn(Schedulers.computation())
                .toCompletable()
    }

    override fun getAccessToken(): Single<String> {
        val token = sharedPreferences.getString(STATE_USER_TOKEN, null)
        return Single.create { emitter ->
            try {
                token.run {
                    emitter.onSuccess(token)
                }
            } catch (exception: Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun getMyselfId(): Single<Int> {
        val id = sharedPreferences.getInt(STATE_USER_ID, -1)
        return Single.create { emitter ->
            try {
                if (id != -1) {
                    emitter.onSuccess(id)
                }
            } catch (exception: Exception) {
                emitter.onError(exception)
            }
        }
    }

    private fun signInValidate(email: String, password: String, isAgreeTerms: Boolean): Completable {
        return Completable.create { emitter ->
            if (email.isBlank() || password.isBlank()) {
                emitter.onError(NotFilledFormsException())
            } else if (!isAgreeTerms) {
                emitter.onError(NotAgreeTermsException())
            } else {
                emitter.onComplete()
            }
        }
    }

    private fun signUpValidate(email: String, name: String, password: String, passwordConfirm: String, isAgreeTerms: Boolean): Completable {
        return Completable.create { emitter ->
            if (email.isBlank() || name.isBlank() || password.isBlank() || passwordConfirm.isBlank()) {
                emitter.onError(NotFilledFormsException())
            } else if (!isAgreeTerms) {
                emitter.onError(NotAgreeTermsException())
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emitter.onError(NotValidEmailException())
            } else if (password.length < 6) {
                emitter.onError(TooShortPasswordException())
            } else if (password != passwordConfirm) {
                emitter.onError(NotSamePasswordException())
            } else {
                emitter.onComplete()
            }
        }
    }

    private fun saveSharedPreferences(user: User) {
        sharedPreferences.edit {
            putString(STATE_USER_TOKEN, user.accessToken)
            putInt(STATE_USER_ID, user.id)
        }
    }

    companion object {
        const val STATE_USER_TOKEN = "userToken"
        const val STATE_USER_ID = "userId"
    }
}