package com.cyder.android.syncpod.viewmodel

import android.databinding.ObservableField
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.repository.RoomRepository
import com.cyder.android.syncpod.util.NotFilledFormsException
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/29.
 */
class CreateRoomActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val repository: RoomRepository
) : ActivityViewModel() {
    var roomName: ObservableField<String?> = ObservableField()
    var roomDescription: ObservableField<String?> = ObservableField()
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

    fun onSubmit() {
        repository.createNewRoom(roomName.get() ?: "", roomDescription.get() ?: "")
                .subscribe({ response ->
                    repository.joinRoom(response.key)
                            .subscribe({
                                navigator.closeActivity()
                                navigator.navigateToRoomActivity(response.key)
                            }, {
                                callback?.onFailed(R.string.network_error)
                            })
                }, { error ->
                    when (error) {
                        is NotFilledFormsException -> callback?.onFailed(R.string.form_not_filled)
                        else -> callback?.onFailed(R.string.network_error)
                    }
                })
    }
}