package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.cyder.atsushi.youtubesync.model.Chat
import com.cyder.atsushi.youtubesync.repository.ChatRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */

class ChatFragmentViewModel @Inject constructor(
        private val navigator: Navigator,
        private val syncPodWsApi: SyncPodWsApi,
        private val repository: ChatRepository
) : FragmentViewModel() {
    var chatViewModels: ObservableList<ChatViewModel> = ObservableArrayList()
    var chat: ObservableField<String> = ObservableField()

    init {
        repository.getPastChats()
                .observeOn(AndroidSchedulers.mainThread())
                .map { convertToViewModel(it) }
                .subscribe({
                    chatViewModels.clear()
                    chatViewModels.addAll(it)
                }, {

                })
        repository.observeChat
                .observeOn(AndroidSchedulers.mainThread())
                .map{ ChatViewModel(ObservableField(it))}
                .subscribe {
                    chatViewModels.add(it)
                    val cpy = chatViewModels.toMutableList()
                    chatViewModels.clear()
                    chatViewModels.addAll(cpy)
                }
    }


    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun sendChat() {
        syncPodWsApi.sendMessage(chat.get() ?: "")
        chat.set("")

    }

    private fun convertToViewModel(chats: List<Chat>): List<ChatViewModel> {
        return chats.map { ChatViewModel(ObservableField(it)) }
    }

}