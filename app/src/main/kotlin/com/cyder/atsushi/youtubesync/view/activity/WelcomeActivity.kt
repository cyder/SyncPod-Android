package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivityWelcomeBinding
import com.cyder.atsushi.youtubesync.viewmodel.WelcomeActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/12.
 */

class WelcomeActivity : BaseActivity() {

    @Inject lateinit var viewModel: WelcomeActivityViewModel
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        binding.viewModel = viewModel
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, WelcomeActivity::class.java)
    }
}