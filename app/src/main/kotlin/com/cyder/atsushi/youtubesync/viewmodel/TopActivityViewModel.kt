package com.cyder.atsushi.youtubesync.viewmodel

import android.util.Log
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.UserRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/12.
 */

class TopActivityViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val roomRepository: RoomRepository,
        private val navigator: Navigator
        ) : ActivityViewModel() {
    override fun onStart() {
        val token = userRepository.getAccessToken()?.blockingGet()!!
        roomRepository.fetchJoinedRooms(token).subscribe { response ->
            response.forEach {
                Log.d("TAG",it.toString())
            }
        }
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun onJoinRoom() {
    }

    fun onCreateRoom() {
    }
}