package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivityUserReportBinding
import com.cyder.atsushi.youtubesync.view.helper.setUpSnackbar
import com.cyder.atsushi.youtubesync.viewmodel.UserReportActivityViewModel
import javax.inject.Inject

class UserReportActivity: BaseActivity() {
        @Inject lateinit var viewModel: UserReportActivityViewModel
        private lateinit var binding: ActivityUserReportBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            getComponent().inject(this)
            bindViewModel(viewModel)

            binding = DataBindingUtil.setContentView(this, R.layout.activity_user_report)
            binding.viewModel = viewModel

            viewModel.roomKey = intent.getStringExtra(ROOM_KEY)
            viewModel.callback = setUpSnackbar()
        }

        override fun onDestroy() {
            viewModel.callback = null
            super.onDestroy()
        }


        companion object {
            private const val ROOM_KEY = "room_key"
            fun createIntent(context: Context, roomKey: String): Intent = Intent(context, UserReportActivity::class.java).putExtra(ROOM_KEY, roomKey)
        }
    }