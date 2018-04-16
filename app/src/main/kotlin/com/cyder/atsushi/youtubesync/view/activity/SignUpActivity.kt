package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivitySignUpBinding
import com.cyder.atsushi.youtubesync.view.helper.setUpSnackbar
import com.cyder.atsushi.youtubesync.viewmodel.SignUpActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/28.
 */

class SignUpActivity : BaseActivity() {
    @Inject lateinit var viewModel: SignUpActivityViewModel
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.viewModel = viewModel

        viewModel.callback = setUpSnackbar()
    }

    override fun onDestroy() {
        viewModel.callback = null
        super.onDestroy()
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SignUpActivity::class.java)
    }
}