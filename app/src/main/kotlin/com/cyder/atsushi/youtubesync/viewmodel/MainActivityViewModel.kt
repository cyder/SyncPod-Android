package com.cyder.atsushi.youtubesync.viewmodel

import com.cyder.atsushi.youtubesync.repository.UserRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/12.
 */

class MainActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val repository: UserRepository
) : ActivityViewModel() {
    override fun onStart() {
    }

    override fun onResume() {
        decideLaunchActivity()
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun onSignInClicked() = navigator.navigateToSignInActivity()

    fun onSignUpClicked() = navigator.navigateToSignUpActivity()

    private fun decideLaunchActivity() {
        repository.getAccessToken()
                .subscribe({
                    navigator.navigateToTopActivity()
                }, {})
    }
}