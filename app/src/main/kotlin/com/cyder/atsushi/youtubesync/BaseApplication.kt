package com.cyder.atsushi.youtubesync

import android.app.Application
import com.cyder.atsushi.youtubesync.di.AppComponent
import com.cyder.atsushi.youtubesync.di.AppModule

/**
 * Created by chigichan24 on 2018/01/11.
 */

class BaseApplication : Application() {
    private lateinit var component: AppComponent

    fun getComponent(): AppComponent = component

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)

    }
}
