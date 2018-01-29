package com.cyder.atsushi.youtubesync.di

import android.content.Context
import com.cyder.atsushi.youtubesync.api.SignInApi
import com.cyder.atsushi.youtubesync.repository.UserDataRepository
import com.cyder.atsushi.youtubesync.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by chigichan24 on 2018/01/12.
 */
@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext(): Context = context

    // TODO create singleton ormadatabase

    @Singleton @Provides
    fun provideUserRepository(
            api: SignInApi,
            name: String,
            password: String
    ): UserRepository = UserDataRepository(api, name, password)
}