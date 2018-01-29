package com.cyder.atsushi.youtubesync.di

import com.cyder.atsushi.youtubesync.api.SignInApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by chigichan24 on 2018/01/29.
 */

@Module
class NetworkModule {
    companion object {
        val instance = NetworkModule()
    }

    @Singleton
    @Provides
    fun provideSignInApi(retrofit: Retrofit): SignInApi {
        return retrofit.create(SignInApi::class.java)
    }
}