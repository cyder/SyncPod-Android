package com.cyder.atsushi.youtubesync.view.helper

import android.content.Intent
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.cyder.atsushi.youtubesync.di.scope.ActivityScope
import com.cyder.atsushi.youtubesync.view.activity.*
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(
        private val activity: AppCompatActivity
) {
    val fragmentManager: FragmentManager = activity.supportFragmentManager

    fun navigateToWelcomeActivity() = activity.startActivity(WelcomeActivity.createIntent(activity))
    fun navigateToSignInActivity() = activity.startActivity(SignInActivity.createIntent(activity))
    fun navigateToSignUpActivity() = activity.startActivity(SignUpActivity.createIntent(activity))
    fun navigateToTopActivity(errorMessageId: Int? = null) {
        val intent = TopActivity.createIntent(activity, errorMessageId)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    fun navigateToCreateRoomActivity() = activity.startActivity(CreateRoomActivity.createIntent(activity))
    fun navigateToRoomActivity(roomKey: String) = activity.startActivity(RoomActivity.createIntent(activity, roomKey))
    fun navigateToSearchVideoActivity() = activity.startActivity(SearchVideoActivity.createIntent(activity))
    fun navigateToUserReportActivity(roomKey: String) = activity.startActivity(UserReportActivity.createIntent(activity, roomKey))
    fun closeActivity() = activity.finish()
}
