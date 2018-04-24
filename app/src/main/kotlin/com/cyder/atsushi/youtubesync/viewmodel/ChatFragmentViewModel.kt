package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.cyder.atsushi.youtubesync.model.Chat
import com.cyder.atsushi.youtubesync.repository.ChatRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */

class ChatFragmentViewModel @Inject constructor(
        private val navigator: Navigator,
        private val repository: ChatRepository
) : FragmentViewModel() {
    var chatViewModels: ObservableList<ChatViewModel> = ObservableArrayList()
    override fun onStart() {
        repository.getPastChats()
                .map { convertToViewModel(it) }
                .subscribe({
                    chatViewModels.clear()
                    chatViewModels.addAll(it)
                }, {

                })
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    private fun convertToViewModel(chats: List<Chat>): List<ChatViewModel> {
        return chats.map { ChatViewModel(ObservableField(it)) }
    }

}