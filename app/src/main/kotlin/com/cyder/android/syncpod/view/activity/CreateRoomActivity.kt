package com.cyder.android.syncpod.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.SimpleAdapter
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.ActivityCreateRoomBinding
import com.cyder.android.syncpod.view.helper.setUpSnackbar
import com.cyder.android.syncpod.viewmodel.CreateRoomActivityViewModel
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/29.
 */

class CreateRoomActivity : BaseActivity() {
    @Inject lateinit var viewModel: CreateRoomActivityViewModel
    private lateinit var binding: ActivityCreateRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_room)
        binding.viewModel = viewModel

        viewModel.resources = this.resources
        viewModel.callback = setUpSnackbar()
        setUpSpinner()
    }

    private fun setUpSpinner() {
        val adapter = SimpleAdapter(
                this,
                viewModel.publishingSettingItems,
                R.layout.spinner_item_with_description,
                viewModel.publishingSettingKeys,
                intArrayOf(R.id.title, R.id.description))
        binding.publishingSetting.adapter = adapter
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, CreateRoomActivity::class.java)
    }
}