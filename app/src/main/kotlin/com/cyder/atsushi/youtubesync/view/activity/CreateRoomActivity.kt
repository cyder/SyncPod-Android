package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent

/**
 * Created by atsushi on 2018/03/29.
 */

class CreateRoomActivity : BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent = Intent(context, CreateRoomActivity::class.java)
    }
}