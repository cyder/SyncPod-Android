package com.cyder.atsushi.youtubesync.viewmodel.base


/**
 * Created by chigichan24 on 2018/04/11.
 */

abstract class FragmentViewModel {
    abstract fun onStart()

    abstract fun onResume()

    abstract fun onPause()

    abstract fun onStop()
}