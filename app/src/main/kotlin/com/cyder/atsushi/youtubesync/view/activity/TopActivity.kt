package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivityTopBinding
import com.cyder.atsushi.youtubesync.viewmodel.TopActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/29.
 */

class TopActivity : BaseActivity() {
    @Inject lateinit var viewModel: TopActivityViewModel
    private lateinit var binding: ActivityTopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_top)
        binding.viewModel = viewModel
    }
    companion object {
        fun createIntent(context: Context): Intent = Intent(context, TopActivity::class.java)
    }
}