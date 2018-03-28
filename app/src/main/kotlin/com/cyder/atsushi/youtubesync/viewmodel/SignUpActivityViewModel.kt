package com.cyder.atsushi.youtubesync.viewmodel

import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/28.
 */

class SignUpActivityViewModel @Inject constructor(
        private val navigator: Navigator
) : ActivityViewModel() {
    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun onBackButtonClicked() = navigator.closeActivity()
}