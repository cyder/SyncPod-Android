package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.model.Room
import com.cyder.atsushi.youtubesync.repository.RoomDataRepository
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/03/17.
 */

class RoomViewModel @Inject constructor(
        val repository: RoomRepository,
        val navigator: Navigator,
        val room: ObservableField<Room>
){
    lateinit var callback: SnackbarCallback
    fun onItemClick() {
        repository.joinRoom(room.get().key)
                .subscribe({
                    navigator.navigateToRoomActivity(room.get().key)
                },{ error ->
                    when(error) {
                        is RoomDataRepository.CannotJoinRoomException -> callback.onFailed(R.string.room_enter_reject_message)
                    }
                })
    }
}