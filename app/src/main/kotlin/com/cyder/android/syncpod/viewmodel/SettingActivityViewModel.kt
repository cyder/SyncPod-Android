package com.cyder.android.syncpod.viewmodel

import android.databinding.ObservableField
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.repository.UserRepository
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by shikibu on 2018/05/21.
 */

class SettingActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val repository: UserRepository
) : ActivityViewModel() {
    var mailAddress: ObservableField<String?> = ObservableField()
    var name: ObservableField<String?> = ObservableField()
    var callback: SnackbarCallback? = null
    var message: ObservableField<String> = ObservableField()

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

    fun onSubmit() {
        repository.editUser(
                mailAddress.get() ?: "",
                name.get() ?: ""
        ).subscribe({
            navigator.navigateToTopActivity()
        }, {
            callback?.onFailed(R.string.sign_up_used_email)
        })
    }
}
