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

    //TODO change to custom getter
    fun getComponent(): ActivityComponent {
        if (component == null) {
            val application = application as BaseApplication
            component = application.component.plus(ActivityModule(this))
        }
        return component as ActivityComponent
    }

    protected fun bindViewModel(viewModel: ActivityViewModel) {
        this.viewModel = viewModel
    }


    override fun onStart() {
        super.onStart()
        viewModel?.onStart()
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