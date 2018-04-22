package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.model.Room
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */
class RoomInfoFragmentViewModel @Inject constructor(
        private val repository: RoomRepository
) : FragmentViewModel() {
    lateinit var roomKey: String
    var isLoading: ObservableBoolean = ObservableBoolean()
    var room: ObservableField<Room> = ObservableField()

    override fun onStart() {
    }

    override fun onResume() {
        getRoom()
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun onRefresh() {
        isLoading.set(true)
        getRoom()
    }

    private fun getRoom() {
        repository.fetch(roomKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    isLoading.set(false)
                    room.set(response)
                }, {
                    isLoading.set(false)
                })
    }

}