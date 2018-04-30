package com.cyder.android.syncpod.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.cyder.android.syncpod.di.FragmentComponent
import com.cyder.android.syncpod.di.FragmentModule
import com.cyder.android.syncpod.view.activity.BaseActivity
import com.cyder.android.syncpod.viewmodel.base.FragmentViewModel

/**
 * Created by chigichan24 on 2018/04/10.
 */

abstract class BaseFragment : Fragment() {
    private var viewModel: FragmentViewModel? = null

    val fragmentComponent: FragmentComponent by lazy {
        getBaseActivity().getComponent().plus(FragmentModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getBaseActivity(): BaseActivity = activity as BaseActivity

    protected fun bindViewModel(viewModel: FragmentViewModel) {
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