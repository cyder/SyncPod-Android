package com.cyder.android.syncpod.viewmodel

import android.databinding.ObservableField
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.repository.UserRepository
import com.cyder.android.syncpod.util.NotAgreeTermsException
import com.cyder.android.syncpod.util.NotFilledFormsException
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
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
    var isAgreeTerms: ObservableField<Boolean> = ObservableField()
    var callback: SnackbarCallback? = null

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
    }

    fun onBackButtonClicked() = navigator.closeActivity()

    fun onSignIn() {
        repository.signIn(
                mailAddress.get() ?: "",
                password.get() ?: "",
                isAgreeTerms.get() ?: false
        ).subscribe({
            navigator.navigateToTopActivity()
        }, { error ->
            when (error) {
                is NotFilledFormsException -> callback?.onFailed(R.string.form_not_filled)
                is NotAgreeTermsException -> callback?.onFailed(R.string.not_agree_terms)
                else -> callback?.onFailed(R.string.login_mistook)
            }
        })
    }

}