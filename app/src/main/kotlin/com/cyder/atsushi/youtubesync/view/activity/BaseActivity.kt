package com.cyder.atsushi.youtubesync.view.activity

import android.support.v7.app.AppCompatActivity
import com.cyder.atsushi.youtubesync.BaseApplication
import com.cyder.atsushi.youtubesync.di.ActivityComponent
import com.cyder.atsushi.youtubesync.di.ActivityModule
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel

/**
 * Created by chigichan24 on 2018/01/11.
 */

abstract class BaseActivity : AppCompatActivity() {
    private var component: ActivityComponent? = null
    private var viewModel: ActivityViewModel? = null

    fun getComponent(): ActivityComponent? {
        component.also {
            val application = application as BaseApplication
            application.getComponent().plus(ActivityModule(this))
        }
        return component
    }

    protected fun bindViewModel(viewModel: ActivityViewModel) {
        this.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        viewModel?.onResume()
    }

    override fun onResume() {
        super.onResume()
        viewModel?.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel?.onPause()
    }

    override fun onStop() {
        super.onStop()
        viewModel?.onStop()
    }

}