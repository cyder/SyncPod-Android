package com.cyder.atsushi.youtubesync

import android.app.Application
import com.cyder.atsushi.youtubesync.di.AppComponent
import com.cyder.atsushi.youtubesync.di.AppModule
import com.cyder.atsushi.youtubesync.di.DaggerAppComponent
import com.cyder.atsushi.youtubesync.di.NetworkModule

/**
 * Created by chigichan24 on 2018/01/11.
 */

class BaseApplication : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule.instance)
                .build()
        component.inject(this)

    }
}
