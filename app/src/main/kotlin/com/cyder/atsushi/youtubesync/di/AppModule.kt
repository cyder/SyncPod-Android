package com.cyder.atsushi.youtubesync.di

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by chigichan24 on 2018/01/12.
 */
@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext(): Context = context

    // TODO create singleton ormadatabase
}