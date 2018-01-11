package com.cyder.atsushi.youtubesync.di

import android.support.v7.app.AppCompatActivity
import dagger.Provides

/**
 * Created by chigichan24 on 2018/01/12.
 */

class ActivityModule(val activity: AppCompatActivity) {
    @Provides
    fun provideActivity(): AppCompatActivity? = activity
}