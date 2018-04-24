package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import android.os.Build
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.UserReportRepository
import com.cyder.atsushi.youtubesync.util.NotFilledFormsException
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

class UserReportActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val userReportRepository: UserReportRepository,
        private val roomRepository: RoomRepository
) : ActivityViewModel() {
    var callback: SnackbarCallback? = null
    var message: ObservableField<String> = ObservableField()
    lateinit var roomKey: String

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
        Singles.zip(
                roomRepository.fetch(roomKey),
                Single.create<String> { emitter ->
                    if ((message.get() ?: "").isNotBlank()) {
                        emitter.onSuccess(message.get())
                    } else {
                        emitter.onError(NotFilledFormsException())
                    }
                }
        )
                .map {
                    """${it.second}

----------------------------------------
ルームキー： ${it.first.key}
ルーム名： ${it.first.name}
ルーム説明： ${it.first.description}
オンラインメンバー： ${it.first.onlineUsers?.map { "${it.name} (ID: ${it.id})" }.toString()}
端末： Android ${Build.VERSION.RELEASE}"""
                }
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
}