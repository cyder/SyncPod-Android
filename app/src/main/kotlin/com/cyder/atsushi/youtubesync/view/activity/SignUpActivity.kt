package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.method.LinkMovementMethod
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivitySignUpBinding
import com.cyder.atsushi.youtubesync.view.helper.hideSoftwareKeyBoard
import com.cyder.atsushi.youtubesync.viewmodel.SignUpActivityViewModel
import com.cyder.atsushi.youtubesync.viewmodel.SnackbarCallback
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/28.
 */

class SignUpActivity : BaseActivity() {
    @Inject lateinit var viewModel: SignUpActivityViewModel
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var callback: SnackbarCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.viewModel = viewModel

        setUpSnackbar()
        binding.termsMessage.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroy() {
        viewModel.callback = null
        super.onDestroy()
    }

    private fun setUpSnackbar() {
        callback = object : SnackbarCallback {
            override fun onFailed(resId: Int) {
                currentFocus.hideSoftwareKeyBoard()
                Snackbar.make(currentFocus,
                        resId,
                        Snackbar.LENGTH_SHORT).apply {
                    setAction(R.string.ok) {
                        dismiss()
                    }
                }.show()
            }
        }
        viewModel.callback = callback
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SignUpActivity::class.java)
    }
}