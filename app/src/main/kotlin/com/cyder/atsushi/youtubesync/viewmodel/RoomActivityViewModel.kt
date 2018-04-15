package com.cyder.atsushi.youtubesync.viewmodel

import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

class RoomActivityViewModel @Inject constructor(
        private val repository: RoomRepository,
        private val navigator: Navigator
) : ActivityViewModel() {
    lateinit var roomKey: String

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
        repository.exitRoom(roomKey)
    }
}