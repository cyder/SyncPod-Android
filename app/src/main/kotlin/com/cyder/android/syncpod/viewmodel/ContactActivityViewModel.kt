package com.cyder.android.syncpod.viewmodel

import androidx.databinding.ObservableField
import android.os.Build
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.repository.UserReportRepository
import com.cyder.android.syncpod.util.NotFilledFormsException
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by shikibu on 2018/05/03.
 */

class ContactActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val userReportRepository: UserReportRepository
) : ActivityViewModel() {
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
        Single.create<String> { emitter ->
            val message = message.get() ?: ""
            if ((message).isNotBlank()) {
                emitter.onSuccess(message)
            } else {
                emitter.onError(NotFilledFormsException())
            }
        }
                .map { createDetailMessage(it) }
                .flatMapCompletable { userReportRepository.sendUserReport(it) }
                .subscribe({
                    navigator.closeActivity()
                }, { error ->
                    when (error) {
                        is NotFilledFormsException -> callback?.onFailed(R.string.form_not_filled)
                        else -> callback?.onFailed(R.string.network_error)
                    }
                })
    }

    private fun createDetailMessage(message: String): String {
        return """$message

----------------------------------------
端末： Android ${Build.VERSION.RELEASE}"""
    }
}
