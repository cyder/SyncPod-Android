package com.cyder.atsushi.youtubesync.viewmodel

import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.UserRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/04/10.
 */

class SplashActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val userRepository: UserRepository,
        private val roomRepository: dagger.Lazy<RoomRepository>
) : ActivityViewModel() {
    var roomKey: String? = null

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
        userRepository.getAccessToken()
                .subscribe({
                    roomKey?.run {
                        roomRepository.get().joinRoom(this)
                                .subscribe({
                                    navigator.navigateToTopActivity()
                                    navigator.navigateToRoomActivity(this)
                                    navigator.closeActivity()
                                }, {
                                    navigator.navigateToTopActivity(R.string.room_enter_reject_message)
                                    navigator.closeActivity()
                                })
                    } ?: apply {
                        navigator.navigateToTopActivity()
                        navigator.closeActivity()
                    }
                }, {
                    navigator.navigateToWelcomeActivity()
                    navigator.closeActivity()
                })
    }
}