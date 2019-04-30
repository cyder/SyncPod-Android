package com.cyder.android.syncpod.viewmodel

import androidx.databinding.ObservableField
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.repository.*
import com.cyder.android.syncpod.util.*
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/28.
 */

class SignUpActivityViewModel @Inject constructor(
        private val repository: UserRepository,
        private val navigator: Navigator
) : ActivityViewModel() {
    var mailAddress: ObservableField<String?> = ObservableField()
    var name: ObservableField<String?> = ObservableField()
    var password: ObservableField<String?> = ObservableField()
    var passwordConfirm: ObservableField<String?> = ObservableField()
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

    fun onSignUp() {
        repository.signUp(
                mailAddress.get() ?: "",
                name.get() ?: "",
                password.get() ?: "",
                passwordConfirm.get() ?: "",
                isAgreeTerms.get() ?: false
        ).subscribe({
            navigator.navigateToTopActivity()
        }, { error ->
            when (error) {
                is NotFilledFormsException -> callback?.onFailed(R.string.form_not_filled)
                is NotAgreeTermsException -> callback?.onFailed(R.string.not_agree_terms)
                is NotValidEmailException -> callback?.onFailed(R.string.sign_up_invalid_email)
                is TooShortPasswordException -> callback?.onFailed(R.string.sign_up_min_password_length)
                is NotSamePasswordException -> callback?.onFailed(R.string.sign_up_invalid_password)
                else -> callback?.onFailed(R.string.sign_up_used_email)
            }
        })
    }
}