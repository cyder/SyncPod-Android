package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.UserRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/29.
 */
class CreateRoomActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val roomRepository: RoomRepository,
        private val userRepository: UserRepository
) : ActivityViewModel() {
    var roomName: ObservableField<String?> = ObservableField()
    var roomDescription: ObservableField<String?> = ObservableField()

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun onBackButtonClicked() = navigator.closeActivity()

    fun onSubmit() {
        val token = userRepository.getAccessToken().blockingGet()!!
        roomRepository.createNewRoom(roomName.get() ?: "", roomDescription.get() ?: "", token)
                .subscribe{ response ->
                    response.key
                }
    }
}