package com.cyder.atsushi.youtubesync.viewmodel

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
    override fun onStart() {
        repository.emitNewChat(Chat(0,0,"","","",null))
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

}