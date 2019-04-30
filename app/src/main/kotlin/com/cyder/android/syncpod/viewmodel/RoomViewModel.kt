package com.cyder.android.syncpod.viewmodel

import androidx.databinding.ObservableField
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.model.Room
import com.cyder.android.syncpod.repository.RoomRepository
import com.cyder.android.syncpod.util.CannotJoinRoomException
import com.cyder.android.syncpod.view.helper.Navigator
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/03/17.
 */

class RoomViewModel @Inject constructor(
        val repository: RoomRepository,
        val navigator: Navigator,
        val room: ObservableField<Room>
) {
    lateinit var callback: SnackbarCallback
    fun onItemClick() {
        val room = room.get() ?: return
        repository.joinRoom(room.key)
                .subscribe({
                    navigator.navigateToRoomActivity(room.key)
                }, { error ->
                    when (error) {
                        is CannotJoinRoomException -> callback.onFailed(R.string.room_enter_reject_message)
                    }
                })
    }
}