package com.cyder.atsushi.youtubesync.viewmodel.base

import android.content.res.Resources

/**
 * Created by chigichan24 on 2018/01/12.
 */

abstract class ActivityViewModel {
    lateinit var resources: Resources

    abstract fun onStart()

    abstract fun onResume()

    abstract fun onPause()

    abstract fun onStop()

    abstract fun onDestroy()
}