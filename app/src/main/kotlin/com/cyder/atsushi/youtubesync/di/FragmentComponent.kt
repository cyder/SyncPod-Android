package com.cyder.atsushi.youtubesync.di

import com.cyder.atsushi.youtubesync.di.scope.FragmentScope
import com.cyder.atsushi.youtubesync.view.fragment.VideoFragment
import dagger.Subcomponent

/**
 * Created by chigichan24 on 2018/01/12.
 */

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    //TODO You have to inject fragment here
    fun inject(fragment: VideoFragment)
}
