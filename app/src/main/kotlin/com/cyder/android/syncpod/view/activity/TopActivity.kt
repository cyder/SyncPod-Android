package com.cyder.android.syncpod.view.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.ActivityTopBinding
import com.cyder.android.syncpod.databinding.ItemRoomBinding
import com.cyder.android.syncpod.view.adapter.BindingHolder
import com.cyder.android.syncpod.view.adapter.ObservableListAdapter
import com.cyder.android.syncpod.view.helper.setUpSnackbar
import com.cyder.android.syncpod.viewmodel.DialogCallback
import com.cyder.android.syncpod.viewmodel.RoomViewModel
import com.cyder.android.syncpod.viewmodel.TopActivityViewModel
import kotlinx.android.synthetic.main.join_room_dialog.view.*
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/29.
 */

class TopActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: TopActivityViewModel

    private lateinit var binding: ActivityTopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_top)
        binding.viewModel = viewModel

        viewModel.errorMessageId = intent.getIntExtra(ERROR_MESSAGE_ID, -1).takeIf { it != -1 }

        setUpDialog()
        viewModel.snackbarCallback = setUpSnackbar()
        initRecyclerView()
    }

    private fun setUpDialog() {
        val callback = object : DialogCallback {
            override fun onAction() {
                val layout = layoutInflater.inflate(R.layout.join_room_dialog, null)
                val dialog = AlertDialog.Builder(this@TopActivity)
                        .setTitle(R.string.join_room)
                        .setView(layout)
                        .setPositiveButton(R.string.send_button) { _, _ ->
                            viewModel.onClickJoinRoomDialogButton(layout.room_key.text.toString())
                        }
                        .setNegativeButton(R.string.cancel_button) { _, _ -> }
                        .create()
                dialog.show()
            }
        }
        viewModel.dialogCallback = callback
    }

    private fun initRecyclerView() {
        binding.joinedRoomRecycler.run {
            this.isNestedScrollingEnabled = false
            val adapter = RoomAdapter(viewModel.joinedRoomViewModels)
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(this@TopActivity)
        }

        binding.popularRoomRecycler.run {
            this.isNestedScrollingEnabled = false
            val adapter = RoomAdapter(viewModel.populardRoomViewModels)
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(this@TopActivity)
        }
    }


    inner class RoomAdapter(list: ObservableList<RoomViewModel>) : ObservableListAdapter<RoomViewModel, BindingHolder<ItemRoomBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<ItemRoomBinding> = BindingHolder(parent, R.layout.item_room)

        override fun onBindViewHolder(holder: BindingHolder<ItemRoomBinding>, position: Int) {
            val viewModel = getItem(position)
            viewModel.callback = setUpSnackbar()
            val binding = holder.binding
            binding.viewModel = viewModel
        }
    }

    companion object {
        const val ERROR_MESSAGE_ID = "error_message_id"
        fun createIntent(context: Context, errorMessageId: Int?): Intent = Intent(context, TopActivity::class.java).putExtra(ERROR_MESSAGE_ID, errorMessageId)
    }
}
