package com.cyder.android.syncpod.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

/**
 * Created by chigichan24 on 2018/01/12.
 */

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideActivity(): AppCompatActivity = activity
}