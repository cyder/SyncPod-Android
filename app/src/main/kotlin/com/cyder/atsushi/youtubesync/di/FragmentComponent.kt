package com.cyder.atsushi.youtubesync.di

import com.cyder.atsushi.youtubesync.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by chigichan24 on 2018/01/12.
 */

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent
