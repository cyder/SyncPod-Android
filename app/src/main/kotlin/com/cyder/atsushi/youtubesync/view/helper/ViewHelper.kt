package com.cyder.atsushi.youtubesync.view.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.ShareCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.viewmodel.DialogCallback
import com.cyder.atsushi.youtubesync.viewmodel.ButtonInterface
import com.cyder.atsushi.youtubesync.viewmodel.ShareCompatCallback
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

fun Activity.setUpShareCompat(): ShareCompatCallback {
    return object : ShareCompatCallback {
        override fun onStart(message: String) {
            ShareCompat.IntentBuilder.from(this@setUpShareCompat)
                    .setText(message)
                    .setType("text/plain")
                    .startChooser()
        }
    }
}

fun Activity.setUpMenuDialog(items: List<ButtonInterface>): DialogCallback {
    val names = items.map { it.name }.toTypedArray()

    val builder = AlertDialog.Builder(this)
            .setItems(names) { _, index ->
                items[index].onClick()
            }
            .create()

    return object : DialogCallback {
        override fun onAction() {
            builder.show()
        }
    }
}

fun Activity.setUpConfirmationDialog(title: String, description: String, positiveButton: ButtonInterface): DialogCallback {
    val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(description)
            .setPositiveButton(positiveButton.name) { _, _ ->
                positiveButton.onClick()
            }
            .setNegativeButton(R.string.cancel_button) { _, _ ->
            }

    return object : DialogCallback {
        override fun onAction() {
            builder.show()
        }
    }
}
