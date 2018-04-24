package com.cyder.atsushi.youtubesync.viewmodel

import android.content.res.Resources
import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.model.User
import javax.inject.Inject

class UserViewModel @Inject constructor(
        val user: ObservableField<User>
) {
    lateinit var callback: DialogCallback
    lateinit var resources: Resources
    val forceExitMenuTitle by lazy {
        resources.getString(R.string.force_exit_menu).format(user.get().name)
    }

    fun onClick() {
        callback.onAction()
    }

    fun onSelectForceExit() {
    }
}
