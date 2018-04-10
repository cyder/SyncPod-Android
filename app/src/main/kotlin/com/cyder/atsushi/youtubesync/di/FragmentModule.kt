package com.cyder.atsushi.youtubesync.di

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.cyder.atsushi.youtubesync.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * Created by chigichan24 on 2018/01/12.
 */
@Module
class FragmentModule(val fragment: Fragment) {
    @Provides
    @FragmentScope
    fun provideFragmentManager(): FragmentManager? = fragment.fragmentManager
}