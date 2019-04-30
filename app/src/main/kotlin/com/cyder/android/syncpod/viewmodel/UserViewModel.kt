package com.cyder.android.syncpod.viewmodel

import android.content.res.Resources
import androidx.databinding.ObservableField
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.model.User
import com.cyder.android.syncpod.repository.RoomRepository
import com.cyder.android.syncpod.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
        val user: ObservableField<User>,
        val userRepository: UserRepository,
        val roomRepository: RoomRepository
) {
    lateinit var menuDialogCallback: DialogCallback
    lateinit var forceExitDialogCallback: DialogCallback
    lateinit var resources: Resources
    val forceExitTitle by lazy {
        resources.getString(R.string.force_exit_title).format(user.get()?.name)
    }
    val forceExitDescription by lazy {
        resources.getString(R.string.force_exit_description).format(user.get()?.name)
    }

    fun onClick() {
        if (userRepository.getMyselfId().blockingGet() != user.get()?.id) {
            menuDialogCallback.onAction()
        }
    }

    fun onSelectForceExit() {
        forceExitDialogCallback.onAction()
    }

    fun onConfirmForceExit() {
        val user = user.get() ?: return
        roomRepository.exitForce(user)
    }
}
