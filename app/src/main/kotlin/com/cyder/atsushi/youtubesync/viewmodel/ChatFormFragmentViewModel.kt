package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.repository.ChatRepository
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import javax.inject.Inject

class ChatFormFragmentViewModel @Inject constructor(
        private val repository: ChatRepository
) : FragmentViewModel() {
    var chat: ObservableField<String> = ObservableField()

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun sendChat() {
        repository.sendChat(chat.get() ?: "")
        chat.set("")
    }
}