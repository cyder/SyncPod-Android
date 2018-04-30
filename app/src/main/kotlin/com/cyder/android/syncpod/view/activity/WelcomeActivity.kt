package com.cyder.android.syncpod.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.ActivityWelcomeBinding
import com.cyder.android.syncpod.viewmodel.WelcomeActivityViewModel
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