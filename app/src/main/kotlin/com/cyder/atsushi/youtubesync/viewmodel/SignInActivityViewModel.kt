package com.cyder.atsushi.youtubesync.viewmodel

import android.view.View
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/17.
 */

class SignInActivityViewModel @Inject constructor(
        val navigator: Navigator
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

}