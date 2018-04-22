package com.cyder.atsushi.youtubesync.viewmodel

import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

class UserReportActivityViewModel @Inject constructor(
        private val navigator: Navigator
) : ActivityViewModel() {
    var callback: SnackbarCallback? = null

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
    }

    fun onBackButtonClicked() = navigator.closeActivity()
}