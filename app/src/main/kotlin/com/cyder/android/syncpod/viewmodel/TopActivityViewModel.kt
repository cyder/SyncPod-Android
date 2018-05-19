package com.cyder.android.syncpod.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.model.Room
import com.cyder.android.syncpod.repository.RoomRepository
import com.cyder.android.syncpod.util.CannotJoinRoomException
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/12.
 */

class TopActivityViewModel @Inject constructor(
        private val roomRepository: RoomRepository,
        private val navigator: Navigator
) : ActivityViewModel() {

    var joinedRoomViewModels: ObservableList<RoomViewModel> = ObservableArrayList()
    var populardRoomViewModels: ObservableList<RoomViewModel> = ObservableArrayList()
    var isLoading: ObservableBoolean = ObservableBoolean()
    var hasEntered: ObservableBoolean = ObservableBoolean(false)
    var hasPopularRoom: ObservableBoolean = ObservableBoolean(false)
    var errorMessageId: Int? = null
    var dialogCallback: DialogCallback? = null
    var snackbarCallback: SnackbarCallback? = null

    override fun onStart() {
        errorMessageId?.run {
            snackbarCallback?.onFailed(this)
        }
    }

    override fun onResume() {
        getRooms()
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    fun onJoinRoom() {
        dialogCallback?.onAction()
    }

    fun onCreateRoom() = navigator.navigateToCreateRoomActivity()

    fun onContactUs() = navigator.navigateToContactActivity()

    fun onRefresh() {
        isLoading.set(true)
        getRooms()
    }

    fun onClickJoinRoomDialogButton(roomKey: String) {
        roomRepository.joinRoom(roomKey)
                .subscribe({
                    navigator.navigateToRoomActivity(roomKey)
                }, { error ->
                    when (error) {
                        is CannotJoinRoomException -> snackbarCallback?.onFailed(R.string.room_enter_reject_message)
                    }
                })
    }

    private fun getRooms() {
        Singles.zip(
                roomRepository.fetchJoinedRooms()
                        .map { convertToViewModel(it) }
                        .observeOn(AndroidSchedulers.mainThread()),
                roomRepository.fetchPopularRooms()
                        .map { convertToViewModel(it) }
                        .observeOn(AndroidSchedulers.mainThread())
        )
                .subscribe({
                    val joinedRooms = it.first
                    joinedRoomViewModels.clear()
                    joinedRoomViewModels.addAll(joinedRooms)
                    hasEntered.set(joinedRooms.isNotEmpty())

                    val popularRooms = it.second
                    populardRoomViewModels.clear()
                    populardRoomViewModels.addAll(popularRooms)
                    hasPopularRoom.set(popularRooms.isNotEmpty())

                    isLoading.set(false)
                }, {
                    isLoading.set(false)
                })
    }

    private fun convertToViewModel(rooms: List<Room>): List<RoomViewModel> {
        return rooms.map { RoomViewModel(roomRepository, navigator, ObservableField(it)) }
    }
}