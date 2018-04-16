package com.cyder.atsushi.youtubesync.view.helper

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.viewmodel.SnackbarCallback

/**
 * Created by chigichan24 on 2018/02/23.
 */

fun View.hideSoftwareKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Activity.setUpSnackbar(): SnackbarCallback {
    return object : SnackbarCallback {
        override fun onFailed(resId: Int) {
            currentFocus.hideSoftwareKeyBoard()
            val snackbar = Snackbar.make(currentFocus,
                    resId,
                    Snackbar.LENGTH_LONG).apply {
                setAction(R.string.ok) {
                    dismiss()
                }
            }
            val snackbarView = snackbar.view
            val tv = snackbarView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            tv.maxLines = 3
            snackbar.show()
        }
    }
}