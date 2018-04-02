package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivityRoomBinding
import com.cyder.atsushi.youtubesync.viewmodel.RoomActivityViewModel
import javax.inject.Inject

class RoomActivity : BaseActivity() {

    @Inject lateinit var viewModel: RoomActivityViewModel
    private lateinit var binding: ActivityRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        binding.viewModel = viewModel

        viewModel.roomKey = intent.getStringExtra(ROOM_KEY)
    }

    companion object {
        const val ROOM_KEY = "room_key"
        fun createIntent(context: Context, roomKey: String): Intent = Intent(context, RoomActivity::class.java).putExtra(ROOM_KEY, roomKey)
    }
}