package com.cyder.android.syncpod.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.cyder.android.syncpod.api.SyncPodApi
import com.cyder.android.syncpod.api.request.SignUp
import com.cyder.android.syncpod.api.response.User
import com.cyder.android.syncpod.util.NotAgreeTermsException
import com.cyder.android.syncpod.util.NotFilledFormsException
import com.cyder.android.syncpod.util.NotSamePasswordException
import com.cyder.android.syncpod.util.NotValidEmailException
import com.cyder.android.syncpod.util.TooShortPasswordException
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

    override fun editUser(email: String, name: String): Completable {
        return editUserValidate(email, name)
                .andThen(syncPodApi.editUser(email, name))
                .subscribeOn(Schedulers.computation())
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
        val id = sharedPreferences.getInt(STATE_USER_ID, -1).takeIf { it != -1 }
        return Single.create { emitter ->
            try {
                id?.apply {
                    emitter.onSuccess(this)
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

    private fun editUserValidate(email: String, name: String): Completable {
        return Completable.create { emitter ->
            if (email.isBlank() || name.isBlank()) {
                emitter.onError(NotFilledFormsException())
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