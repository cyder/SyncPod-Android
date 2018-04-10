package com.cyder.atsushi.youtubesync.viewmodel

import com.cyder.atsushi.youtubesync.repository.UserRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/04/10.
 */

class SplashActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val repository: UserRepository
) : ActivityViewModel() {
    override fun onStart() {
        decideLaunchActivity()
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    private fun decideLaunchActivity() {
        repository.getAccessToken()
                .subscribe({
                    navigator.navigateToTopActivity()
                    navigator.closeActivity()
                }, {
                    navigator.navigateToMainActivity()
                    navigator.closeActivity()
                })
    }
}