package com.cyder.android.syncpod.di

import com.cyder.android.syncpod.BaseApplication
import dagger.Component
import javax.inject.Singleton

/**
 * Created by chigichan24 on 2018/01/11.
 */

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class
])
interface AppComponent {
    fun inject(application: BaseApplication)
    fun plus(module: ActivityModule): ActivityComponent
}