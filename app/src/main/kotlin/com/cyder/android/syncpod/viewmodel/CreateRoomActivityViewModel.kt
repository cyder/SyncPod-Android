package com.cyder.android.syncpod.viewmodel

import android.content.res.Resources
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
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
    var publishingSetting = ObservableInt()
    lateinit var resources: Resources
    val publishingSettingItems: MutableList<HashMap<String, String>> by lazy {
        val publicRoom = hashMapOf(
                ID to PUBLIC_ROOM,
                TITLE to resources.getString(R.string.public_room),
                DESCRIPTION to resources.getString(R.string.public_room_description))
        val privateRoom = hashMapOf(
                ID to PRIVATE_ROOM,
                TITLE to resources.getString(R.string.private_room),
                DESCRIPTION to resources.getString(R.string.private_room_description))
        mutableListOf(publicRoom, privateRoom)
    }

    val publishingSettingKeys = arrayOf(TITLE, DESCRIPTION)
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
        val isPublic = publishingSettingItems[publishingSetting.get()][ID] == PUBLIC_ROOM

        repository.createNewRoom(roomName.get() ?: "", roomDescription.get() ?: "", isPublic)
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

    companion object {
        private const val ID = "id"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val PUBLIC_ROOM = "public_room"
        private const val PRIVATE_ROOM = "private_room"
    }
}