package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.cyder.atsushi.youtubesync.model.Chat
import com.cyder.atsushi.youtubesync.repository.ChatRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */

class ChatFragmentViewModel @Inject constructor(
        private val navigator: Navigator,
        private val repository: ChatRepository
) : FragmentViewModel() {
    var chatViewModels: ObservableList<ChatViewModel> = ObservableArrayList()
    var chat: ObservableField<String> = ObservableField()
    private val onPauseSubject = PublishSubject.create<Unit>()

    override fun onStart() {
    }

    override fun onResume() {
        repository.observePastChat
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(onPauseSubject.toFlowable(BackpressureStrategy.LATEST))
                .map { convertToViewModel(it) }
                .subscribe({
                    chatViewModels.clear()
                    chatViewModels.addAll(it)
                }, {

                })
        repository.observeChat
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(onPauseSubject.toFlowable(BackpressureStrategy.LATEST))
                .map {
                    ChatViewModel(ObservableField(it))
                }
                .subscribe {
                    chatViewModels.add(it)
                    val cpy = chatViewModels.toMutableList()
                    chatViewModels.clear()
                    chatViewModels.addAll(cpy)
                }
    }

    override fun onPause() {
        onPauseSubject.onNext(INVOCATION)
    }

    override fun onStop() {
    }

    fun sendChat() {
        repository.sendChat(chat.get() ?: "")
        chat.set("")
    }

    private fun convertToViewModel(chats: List<Chat>): List<ChatViewModel> {
        return chats.map { ChatViewModel(ObservableField(it)) }
    }

    companion object {
        val INVOCATION = Unit
    }

}