package com.cyder.atsushi.youtubesync.api

import com.cyder.atsushi.youtubesync.api.request.SignUp
import com.cyder.atsushi.youtubesync.api.response.Response
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Singleton

/**
 * Created by chigichan24 on 2018/01/18.
 */

@Singleton
interface SyncPodApi {
    @POST("login")
    fun signIn(@Query("email") email: String, @Query("password") password: String): Single<Response>

    @POST("users")
    fun signUp(@Body user: SignUp): Single<Response>
}