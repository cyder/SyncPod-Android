package com.cyder.android.syncpod.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.ActivityContactBinding
import com.cyder.android.syncpod.view.helper.setUpSnackbar
import com.cyder.android.syncpod.viewmodel.ContactActivityViewModel
import javax.inject.Inject

/**
 * Created by shikibu on 2018/05/03.
 */

class ContactActivity: BaseActivity() {
    @Inject lateinit var viewModel: ContactActivityViewModel
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)
        binding.viewModel = viewModel

        viewModel.callback = setUpSnackbar()
    }

    override fun onDestroy() {
        viewModel.callback = null
        super.onDestroy()
    }


    companion object {
        fun createIntent(context: Context): Intent = Intent(context, ContactActivity::class.java)
    }
}