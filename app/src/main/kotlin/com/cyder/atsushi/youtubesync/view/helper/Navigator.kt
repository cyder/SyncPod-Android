package com.cyder.atsushi.youtubesync.view.helper

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.cyder.atsushi.youtubesync.di.scope.ActivityScope
import com.cyder.atsushi.youtubesync.view.activity.CreateRoomActivity
import com.cyder.atsushi.youtubesync.view.activity.RoomActivity
import com.cyder.atsushi.youtubesync.view.activity.SignInActivity
import com.cyder.atsushi.youtubesync.view.activity.SignUpActivity
import com.cyder.atsushi.youtubesync.view.activity.TopActivity
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(
        private val activity: AppCompatActivity
) {
    val fragmentManager: FragmentManager = activity.supportFragmentManager

    fun navigateToSignInActivity() = activity.startActivity(SignInActivity.createIntent(activity))
    fun navigateToSignUpActivity() = activity.startActivity(SignUpActivity.createIntent(activity))
    fun navigateToTopActivity() {
        val intent = TopActivity.createIntent(activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    fun navigateToCreateRoomActivity() = activity.startActivity(CreateRoomActivity.createIntent(activity))
    fun navigateToRoomActivity(roomKey: String) = activity.startActivity(RoomActivity.createIntent(activity, roomKey))
    fun closeActivity() = activity.finish()
}
