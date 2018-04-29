package com.cyder.atsushi.youtubesync.viewmodel.base

import android.content.res.Resources
import io.reactivex.subjects.PublishSubject

/**
 * Created by chigichan24 on 2018/01/12.
 */

abstract class ActivityViewModel {
    lateinit var resources: Resources
    protected val onPauseSubject = PublishSubject.create<Unit>()!!

    abstract fun onStart()

    abstract fun onResume()

    open fun onPause() {
        onPauseSubject.onNext(INVOCATION)
    }

    abstract fun onStop()

    abstract fun onDestroy()

    companion object {
        val INVOCATION = Unit
    }
}