package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import android.util.Log
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import javax.inject.Inject

class JoinRoomDialogViewModel @Inject constructor(
        val repository: RoomRepository,
        val navigator: Navigator
){
    var roomKey: ObservableField<String?> = ObservableField()

    fun onClickJoinRoomAlertButton() {
        Log.d("App", roomKey.get() ?: "failed")
    }
}