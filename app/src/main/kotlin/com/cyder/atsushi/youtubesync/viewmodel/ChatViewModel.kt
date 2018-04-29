package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import com.cyder.atsushi.youtubesync.model.Chat
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/25.
 */

class ChatViewModel @Inject constructor(
        val chat: ObservableField<Chat>
)
