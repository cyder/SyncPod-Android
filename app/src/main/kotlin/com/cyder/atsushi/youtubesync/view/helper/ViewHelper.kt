package com.cyder.atsushi.youtubesync.view.helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by chigichan24 on 2018/02/23.
 */

fun View.hideSoftwareKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}