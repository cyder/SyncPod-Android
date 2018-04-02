package com.cyder.atsushi.youtubesync.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.repository.RoomDataRepository
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.UserDataRepository
import com.cyder.atsushi.youtubesync.repository.UserRepository
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
            consumer: Consumer,
            repository: UserRepository
    ) : RoomRepository = RoomDataRepository(api, consumer, repository.getAccessToken().blockingGet()!!)

    companion object {
        const val APP_NAME = "youtube-sync"
    }
}