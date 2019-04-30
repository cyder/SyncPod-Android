package com.cyder.android.syncpod

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.cyder.android.syncpod.di.AppComponent
import com.cyder.android.syncpod.di.AppModule
import com.cyder.android.syncpod.di.DaggerAppComponent
import com.cyder.android.syncpod.di.NetworkModule

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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
