package com.cyder.atsushi.youtubesync.di

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.repository.UserRepository
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApiImpl
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI
import javax.inject.Singleton

/**
 * Created by chigichan24 on 2018/01/29.
 */

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .build()
    }

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
    fun provideSyncPodApi(@RetrofitApi retrofit: Retrofit): SyncPodApi {
        return retrofit.create(SyncPodApi::class.java)
    }

    @Singleton
    @Provides
    fun provideConsumer(
            repository: UserRepository
    ): Consumer {
        val token = repository.getAccessToken().blockingGet()!!
        val options = Consumer.Options()
        options.connection.query = mapOf(TOKEN to token)
        return ActionCable.createConsumer(URI(WS_URL), options)
    }

    @Singleton
    @Provides
    fun provideSyncPodWsApi(
            subscription: Subscription
    ) : SyncPodWsApi = SyncPodWsApiImpl(subscription)

    companion object {
        val instance = NetworkModule()
        const val BASE_URL = "https://api.sync-pod.com/api/v1/"
        const val WS_URL = "https://api.sync-pod.com/cable/"
        const val TOKEN = "token"
    }
}