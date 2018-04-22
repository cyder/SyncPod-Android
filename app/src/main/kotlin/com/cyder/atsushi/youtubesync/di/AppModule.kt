package com.cyder.atsushi.youtubesync.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.repository.*
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import com.hosopy.actioncable.Consumer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by chigichan24 on 2018/01/12.
 */
@Module
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideContext(): Context = context

    // TODO create singleton ormadatabase

    @Singleton
    @Provides
    fun provideUserRepository(
            api: SyncPodApi
    ): UserRepository {
        val pref = context.getSharedPreferences(APP_NAME, MODE_PRIVATE)
        return UserDataRepository(api, pref)
    }

    @Singleton
    @Provides
    fun provideRoomRepository(
            api: SyncPodApi,
            wsApi: SyncPodWsApi,
            consumer: Consumer,
            repository: UserRepository
    ): RoomRepository = RoomDataRepository(api, wsApi, consumer, repository.getAccessToken().blockingGet()!!)

    @Singleton
    @Provides
    fun provideVideoRepository(
            consumer: Consumer,
            syncPodWsApi: SyncPodWsApi
    ): VideoRepository = VideoDataRepository(consumer, syncPodWsApi)

    @Singleton
    @Provides
    fun provideChatRepository(
            syncPodWsApi: SyncPodWsApi
    ): ChatRepository = ChatDataRepository(syncPodWsApi)

    @Singleton
    @Provides
    fun provideYouTubeRepository(
            api: SyncPodApi,
            repository: UserRepository
    ) : YouTubeRepository = YouTubeDataRepository(api, repository.getAccessToken().blockingGet()!!)

    companion object {
        const val APP_NAME = "youtube-sync"
    }
}