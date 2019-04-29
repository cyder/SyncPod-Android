package com.cyder.android.syncpod.viewmodel

import androidx.databinding.ObservableField
import android.os.Build
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.model.Room
import com.cyder.android.syncpod.repository.RoomRepository
import com.cyder.android.syncpod.repository.UserReportRepository
import com.cyder.android.syncpod.util.NotFilledFormsException
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class UserReportActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val userReportRepository: UserReportRepository,
        private val roomRepository: RoomRepository
) : ActivityViewModel() {
    var callback: SnackbarCallback? = null
    var message: ObservableField<String> = ObservableField()
    var room: BehaviorSubject<Room> = BehaviorSubject.create()
    lateinit var roomKey: String

    override fun onStart() {

    }

    override fun onResume() {
        roomRepository.fetch(roomKey)
                .subscribe({
                    room.onNext(it)
                }, {
                    room.onError(it)
                })
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
                room.firstOrError(),
                Single.create<String> { emitter ->
                    val message = message.get() ?: ""
                    if ((message).isNotBlank()) {
                        emitter.onSuccess(message)
                    } else {
                        emitter.onError(NotFilledFormsException())
                    }
                }
        )
                .map { createDetailMessage(it.first, it.second) }
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

    private fun createDetailMessage(room: Room, message: String): String {
        return """$message

----------------------------------------
ルームキー： ${room.key}
ルーム名： ${room.name}
ルーム説明： ${room.description}
オンラインメンバー： ${room.onlineUsers?.map { "${it.name} (ID: ${it.id})" }.toString()}
端末： Android ${Build.VERSION.RELEASE}"""
    }
}