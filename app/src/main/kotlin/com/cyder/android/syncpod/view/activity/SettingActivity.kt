package com.cyder.android.syncpod.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.ActivitySettingBinding
import com.cyder.android.syncpod.view.helper.setUpSnackbar
import com.cyder.android.syncpod.viewmodel.SettingActivityViewModel
import javax.inject.Inject

/**
 * Created by shikibu on 2018/05/21.
 */

class SettingActivity: BaseActivity() {
    @Inject
    lateinit var viewModel: SettingActivityViewModel
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        binding.viewModel = viewModel

        viewModel.callback = setUpSnackbar()
    }

    override fun onDestroy() {
        viewModel.callback = null
        super.onDestroy()
    }


    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SettingActivity::class.java)
    }
}
