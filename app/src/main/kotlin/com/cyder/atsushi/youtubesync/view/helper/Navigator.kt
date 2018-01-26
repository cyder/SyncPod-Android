package com.cyder.atsushi.youtubesync.view.helper

import android.support.v7.app.AppCompatActivity
import com.cyder.atsushi.youtubesync.di.scope.ActivityScope
import com.cyder.atsushi.youtubesync.view.activity.SignInActivity
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(
        private val activity: AppCompatActivity
) {

    fun navigateToSignInActivity() = activity.startActivity(SignInActivity.createIntent(activity))

    fun closeActivity() = activity.finish()
}
