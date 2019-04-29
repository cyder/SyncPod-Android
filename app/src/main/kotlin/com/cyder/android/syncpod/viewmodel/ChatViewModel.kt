package com.cyder.android.syncpod.viewmodel

import androidx.databinding.ObservableField
import com.cyder.android.syncpod.model.Chat
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/25.
 */

class ChatViewModel @Inject constructor(
        val chat: ObservableField<Chat>
)
