package com.cyder.atsushi.youtubesync.view.helper

import android.support.v7.app.AppCompatActivity
import com.cyder.atsushi.youtubesync.di.scope.ActivityScope
import com.cyder.atsushi.youtubesync.view.activity.SignInActivity
import com.cyder.atsushi.youtubesync.view.activity.SignUpActivity
import com.cyder.atsushi.youtubesync.view.activity.TopActivity
import com.cyder.atsushi.youtubesync.view.activity.CreateRoomActivity
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(
        private val activity: AppCompatActivity
) {

    fun navigateToSignInActivity() = activity.startActivity(SignInActivity.createIntent(activity))
    fun navigateToSignUpActivity() = activity.startActivity(SignUpActivity.createIntent(activity))
    fun navigateToTopActivity() = activity.startActivity(TopActivity.createIntent(activity))

    fun navigateToCreateRoom() = activity.startActivity(CreateRoomActivity.createIntent(activity))
    fun closeActivity() = activity.finish()
}
