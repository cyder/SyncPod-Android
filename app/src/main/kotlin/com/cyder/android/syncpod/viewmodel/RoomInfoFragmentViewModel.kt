package com.cyder.android.syncpod.viewmodel

import android.content.res.Resources
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.model.Room
import com.cyder.android.syncpod.repository.RoomRepository
import com.cyder.android.syncpod.repository.UserRepository
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.FragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */
class RoomInfoFragmentViewModel @Inject constructor(
        private val roomRepository: RoomRepository,
        private val userRepository: UserRepository,
        private val navigator: Navigator
) : FragmentViewModel() {
    lateinit var roomKey: String
    lateinit var resources: Resources
    var isLoading: ObservableBoolean = ObservableBoolean()
    var room: ObservableField<Room> = ObservableField()
    var onlineUserTitle: ObservableField<String> = ObservableField()
    var shareCompatCallback: ShareCompatCallback? = null
    var userViewModels: ObservableList<UserViewModel> = ObservableArrayList()

    override fun onStart() {
    }

    override fun onResume() {
        getRoomInfo()
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun onRefresh() {
        isLoading.set(true)
        getRoomInfo()
    }

    fun shareRoom() {
        val shareMessage = resources.getString(R.string.share_room_key_message)
                .format(room.get()?.name, room.get()?.key)
        shareCompatCallback?.onStart(shareMessage)
    }

    fun userReport() {
        navigator.navigateToUserReportActivity(roomKey)
    }

    private fun getRoomInfo() {
        roomRepository.fetch(roomKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    isLoading.set(false)
                    room.set(response)
                    response.onlineUsers?.apply {
                        val title = resources.getString(R.string.online_users_title)
                                .format(this.size)
                        onlineUserTitle.set(title)

                        userViewModels.clear()
                        this.forEach {
                            userViewModels.add(UserViewModel(ObservableField(it), userRepository, roomRepository))
                        }
                    }
                }, {
                    isLoading.set(false)
                })
    }
}