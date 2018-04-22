package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivitySearchVideoBinding
import com.cyder.atsushi.youtubesync.viewmodel.SearchVideoActivityViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/23.
 */

class SearchVideoActivity : BaseActivity() {
    @Inject lateinit var viewModel: SearchVideoActivityViewModel
    private lateinit var binding: ActivitySearchVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_video)
        binding.viewModel = viewModel
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SearchVideoActivity::class.java)
    }
}