package com.cyder.android.syncpod.viewmodel

import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.repository.RoomRepository
import com.cyder.android.syncpod.repository.UserRepository
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
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

    override fun onDestroy() {
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