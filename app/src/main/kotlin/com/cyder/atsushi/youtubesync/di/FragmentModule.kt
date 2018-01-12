package com.cyder.atsushi.youtubesync.di

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import dagger.Module
import dagger.Provides

/**
 * Created by chigichan24 on 2018/01/12.
 */
@Module
class FragmentModule(val fragment: Fragment) {
    @Provides
    fun provideFragmentManager(): FragmentManager = fragment.fragmentManager
}