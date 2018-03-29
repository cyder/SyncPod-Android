package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivityCreateRoomBinding
import com.cyder.atsushi.youtubesync.viewmodel.CreateRoomActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/29.
 */

class CreateRoomActivity : BaseActivity() {
    @Inject lateinit var viewModel: CreateRoomActivityViewModel
    private lateinit var binding: ActivityCreateRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_room)
        binding.viewModel = viewModel
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, CreateRoomActivity::class.java)
    }
}