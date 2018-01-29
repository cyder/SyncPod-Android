package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cyder.atsushi.youtubesync.viewmodel.TopActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/29.
 */

class TopActivity : BaseActivity() {
    @Inject lateinit var viewModel: TopActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

    }
    companion object {
        fun createIntent(context: Context): Intent = Intent(context, TopActivity::class.java)
    }
}