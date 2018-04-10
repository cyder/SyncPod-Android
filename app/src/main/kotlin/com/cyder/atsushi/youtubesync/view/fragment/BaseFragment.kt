package com.cyder.atsushi.youtubesync.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.cyder.atsushi.youtubesync.di.FragmentComponent
import com.cyder.atsushi.youtubesync.di.FragmentModule
import com.cyder.atsushi.youtubesync.view.activity.BaseActivity

/**
 * Created by chigichan24 on 2018/04/10.
 */

abstract class BaseFragment : Fragment() {
    private var component: FragmentComponent? = null

    val fragmentComponent: FragmentComponent by lazy {
        getBaseActivity().getComponent().plus(FragmentModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getBaseActivity(): BaseActivity = activity as BaseActivity

}