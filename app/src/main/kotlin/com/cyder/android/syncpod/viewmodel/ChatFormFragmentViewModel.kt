package com.cyder.android.syncpod.viewmodel

import androidx.databinding.ObservableField
import com.cyder.android.syncpod.repository.ChatRepository
import com.cyder.android.syncpod.viewmodel.base.FragmentViewModel
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