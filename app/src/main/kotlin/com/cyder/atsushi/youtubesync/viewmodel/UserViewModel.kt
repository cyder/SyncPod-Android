package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import android.util.Log
import com.cyder.atsushi.youtubesync.model.User
import javax.inject.Inject

class UserViewModel @Inject constructor(
        val user: ObservableField<User>
) {
    lateinit var callback: DialogCallback

    fun onClick() {
        callback.onAction()
    }

    fun onSelectForceExit() {
    }
}
