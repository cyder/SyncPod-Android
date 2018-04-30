package com.cyder.android.syncpod.viewmodel

import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/12.
 */

class WelcomeActivityViewModel @Inject constructor(
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

    override fun onDestroy() {
    }

    fun onSignInClicked() = navigator.navigateToSignInActivity()

    fun onSignUpClicked() = navigator.navigateToSignUpActivity()
}