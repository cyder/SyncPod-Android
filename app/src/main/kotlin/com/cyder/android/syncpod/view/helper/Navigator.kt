package com.cyder.android.syncpod.view.helper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.cyder.android.syncpod.di.scope.ActivityScope
import com.cyder.android.syncpod.view.activity.ContactActivity
import com.cyder.android.syncpod.view.activity.CreateRoomActivity
import com.cyder.android.syncpod.view.activity.RoomActivity
import com.cyder.android.syncpod.view.activity.SearchVideoActivity
import com.cyder.android.syncpod.view.activity.SignInActivity
import com.cyder.android.syncpod.view.activity.SignUpActivity
import com.cyder.android.syncpod.view.activity.TopActivity
import com.cyder.android.syncpod.view.activity.UserReportActivity
import com.cyder.android.syncpod.view.activity.WelcomeActivity
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
    fun navigateToContactActivity() = activity.startActivity(ContactActivity.createIntent(activity))
    fun closeActivity() = activity.finish()
}
