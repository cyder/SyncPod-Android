package com.cyder.atsushi.youtubesync.viewmodel

import android.support.design.widget.Snackbar
import android.view.View
import com.cyder.atsushi.youtubesync.repository.UserRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/17.
 */

class SignInActivityViewModel @Inject constructor(
        private val repository: UserRepository,
        private val navigator: Navigator
): ActivityViewModel() {
    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    fun onBackButtonClicked(view: View) = navigator.closeActivity()

    fun onSignIn(view: View) {
        repository.signIn()
                .subscribe({
                    navigator.navigateToTopActivity()
                },{
                    Snackbar.make(view, "missed signin", Snackbar.LENGTH_SHORT).show()
                })
    }

}