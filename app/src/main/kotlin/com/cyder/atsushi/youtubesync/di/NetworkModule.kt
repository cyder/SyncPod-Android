package com.cyder.atsushi.youtubesync.di

import com.cyder.atsushi.youtubesync.api.SignInApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by chigichan24 on 2018/01/29.
 */

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient = OkHttpClient.Builder().build()

    @RetrofitApi
    @Singleton
    @Provides
    fun provideRetrofitApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun provideSignInApi(@RetrofitApi retrofit: Retrofit): SignInApi {
        return retrofit.create(SignInApi::class.java)
    }

    companion object {
        val instance = NetworkModule()
        const val BASE_URL = "http://59.106.220.89:3000/api/v1/"
    }
}