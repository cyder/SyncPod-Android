package com.cyder.atsushi.youtubesync.view.activity

import android.os.Bundle
import com.cyder.atsushi.youtubesync.viewmodel.SplashActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/04/10.
 */

class SplashActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: SplashActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)
    }
}