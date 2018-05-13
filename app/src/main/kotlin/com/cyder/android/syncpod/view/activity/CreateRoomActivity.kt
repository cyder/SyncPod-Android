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

        viewModel.callback = setUpSnackbar()
        setUpSpinner()
    }

    private fun setUpSpinner() {
        val publicRoom = hashMapOf(
                TITLE to resources.getString(R.string.public_room),
                DESCRIPTION to resources.getString(R.string.public_room_description))
        val privateRoom = hashMapOf(
                TITLE to resources.getString(R.string.private_room),
                DESCRIPTION to resources.getString(R.string.private_room_description))
        val items = mutableListOf(publicRoom, privateRoom)
        val keys = arrayOf(TITLE, DESCRIPTION)
        val adapter = SimpleAdapter(this, items, R.layout.spinner_item_with_description, keys, intArrayOf(R.id.title, R.id.description))
        binding.publishingSetting.adapter = adapter
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, CreateRoomActivity::class.java)
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
    }
}