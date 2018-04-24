package com.cyder.atsushi.youtubesync.viewmodel

import android.content.res.Resources
import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.model.User
import javax.inject.Inject

class UserViewModel @Inject constructor(
        val user: ObservableField<User>
) {
    lateinit var menuDialogCallback: DialogCallback
    lateinit var forceExitDialogCallback: DialogCallback
    lateinit var resources: Resources
    val forceExitTitle by lazy {
        resources.getString(R.string.force_exit_title).format(user.get().name)
    }
    val forceExitDescription by lazy {
        resources.getString(R.string.force_exit_description).format(user.get().name)
    }

    fun onClick() {
        menuDialogCallback.onAction()
    }

    fun onSelectForceExit() {
        forceExitDialogCallback.onAction()
    }

    fun onConfirmForceExit() {

    }
}
