package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.inputmethod.InputMethodManager
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivitySignInBinding
import com.cyder.atsushi.youtubesync.viewmodel.SignInActivityViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/01/17.
 */

class SignInActivity : BaseActivity() {

    @Inject lateinit var viewModel: SignInActivityViewModel
    private lateinit var binding: ActivitySignInBinding
    private lateinit var callback: SignInActivityViewModel.SnackbarCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.viewModel = viewModel

        setUpSnackbar()
    }

    override fun onDestroy() {
        viewModel.callback = null
        super.onDestroy()
    }

    private fun setUpSnackbar() {
        callback = object : SignInActivityViewModel.SnackbarCallback {
            override fun onSignInFailed() {
                hideSoftwareKeyBoard()
                Snackbar.make(currentFocus,
                        R.string.login_mistook,
                        Snackbar.LENGTH_SHORT).apply {
                    setAction(R.string.ok) {
                        dismiss()
                    }
                }.show()
            }
        }
        viewModel.callback = callback
    }

    private fun hideSoftwareKeyBoard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }


    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SignInActivity::class.java)
    }
}