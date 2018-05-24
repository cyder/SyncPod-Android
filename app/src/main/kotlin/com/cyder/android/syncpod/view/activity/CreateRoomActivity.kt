package com.cyder.android.syncpod.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.ActivityCreateRoomBinding
import com.cyder.android.syncpod.model.PublishingSettingItem
import com.cyder.android.syncpod.view.helper.setUpSnackbar
import com.cyder.android.syncpod.viewmodel.CreateRoomActivityViewModel
import kotlinx.android.synthetic.main.spinner_item_with_description.view.*
import javax.inject.Inject

/**
 * Created by atsushi on 2018/03/29.
 */

class CreateRoomActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: CreateRoomActivityViewModel
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
        val adapter = PublishingSettingSpinnerAdapter(
                this,
                R.layout.spinner_item_with_description,
                viewModel.publishingSettingItems)
        binding.publishingSetting.adapter = adapter
    }

    inner class PublishingSettingSpinnerAdapter(
            context: Context,
            private val resourceId: Int,
            val list: List<PublishingSettingItem>
    ) : ArrayAdapter<PublishingSettingItem>(context, resourceId, list) {
        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return getCustomView(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return getCustomView(position, convertView, parent)
        }

        private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val item = getItem(position)
            val view = convertView ?: layoutInflater.inflate(resourceId, parent, false)

            view.title.text = item.title
            view.description.text = item.description
            return view
        }
    }


    companion object {
        fun createIntent(context: Context): Intent = Intent(context, CreateRoomActivity::class.java)
    }
}