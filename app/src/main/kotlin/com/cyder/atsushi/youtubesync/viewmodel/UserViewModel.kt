package com.cyder.atsushi.youtubesync.viewmodel

import android.content.res.Resources
import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.model.User
import com.cyder.atsushi.youtubesync.repository.UserRepository
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import javax.inject.Inject

class UserViewModel @Inject constructor(
        val user: ObservableField<User>,
        val repository: UserRepository,
        private val wsApi: SyncPodWsApi
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
        if (repository.getMyselfId().blockingGet() != user.get().id) {
            menuDialogCallback.onAction()
        }
    }

    fun onSelectForceExit() {
        forceExitDialogCallback.onAction()
    }

    fun onConfirmForceExit() {
        wsApi.exitForce(user.get())
    }
}
