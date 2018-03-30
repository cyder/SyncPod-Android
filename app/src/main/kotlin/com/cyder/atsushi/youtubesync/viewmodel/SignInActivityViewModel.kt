package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.repository.NotFilledFormsException
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
) : ActivityViewModel() {
    var mailAddress: ObservableField<String?> = ObservableField()
    var password: ObservableField<String?> = ObservableField()
    var callback: SnackbarCallback? = null

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    fun onBackButtonClicked() = navigator.closeActivity()

    fun onSignIn() {
        repository.signIn(mailAddress.get() ?: "", password.get() ?: "")
                .subscribe({
                    navigator.closeActivity()
                    navigator.navigateToTopActivity()
                }, { error ->
                    when (error) {
                        is NotFilledFormsException -> callback?.onFailed(R.string.form_not_filled)
                        else -> callback?.onFailed(R.string.sign_up_used_email)
                    }
                })
    }

}