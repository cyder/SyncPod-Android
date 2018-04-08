package com.cyder.atsushi.youtubesync.view.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivityTopBinding
import com.cyder.atsushi.youtubesync.databinding.ItemRoomBinding
import com.cyder.atsushi.youtubesync.databinding.JoinRoomDialogBinding
import com.cyder.atsushi.youtubesync.view.adapter.BindingHolder
import com.cyder.atsushi.youtubesync.view.adapter.ObservableListAdapter
import com.cyder.atsushi.youtubesync.view.helper.hideSoftwareKeyBoard
import com.cyder.atsushi.youtubesync.viewmodel.*
import java.security.AccessController.getContext
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/29.
 */

class TopActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: TopActivityViewModel
    @Inject
    lateinit var dialogViewModel: JoinRoomDialogViewModel

    private lateinit var binding: ActivityTopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_top)
        binding.viewModel = viewModel

        setUpDialog(viewModel)
        initRecyclerView()
    }

    private fun setUpDialog(viewModel: TopActivityViewModel) {
        val callback = object : DialogCallback {
            override fun onAction() {
                val dialog = AlertDialog.Builder(this@TopActivity)
                val dialogBinding = DataBindingUtil.inflate<JoinRoomDialogBinding>(LayoutInflater.from(dialog.context), R.layout.join_room_dialog, null, false)
                dialogBinding.viewModel = dialogViewModel
                dialog.setTitle(R.string.join_room)
                        .setView(dialogBinding.root)
                        .setPositiveButton(R.string.send_button) { _, _ ->
                            dialogViewModel.onClickJoinRoomAlertButton()
                        }
                        .setNegativeButton(R.string.cancel_button) { _, _ -> }
                        .create()
                dialog.show()
            }
        }
        viewModel.callback = callback
    }

    private fun initRecyclerView() {
        binding.joinedRoomRecycler.isNestedScrollingEnabled = false
        val adapter = JoinedRoomAdapter(viewModel.roomViewModels)
        binding.joinedRoomRecycler.adapter = adapter
        binding.joinedRoomRecycler.layoutManager = LinearLayoutManager(this)
    }


    inner class JoinedRoomAdapter(list: ObservableList<RoomViewModel>) : ObservableListAdapter<RoomViewModel, BindingHolder<ItemRoomBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemRoomBinding> = BindingHolder(parent, R.layout.item_room)

        override fun onBindViewHolder(holder: BindingHolder<ItemRoomBinding>?, position: Int) {
            val viewModel = getItem(position)
            setUpSnackbar(viewModel)
            val binding = holder?.binding
            binding?.viewModel = viewModel
        }

        private fun setUpSnackbar(viewmodel: RoomViewModel) {
            val callback = object : SnackbarCallback {
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
            viewmodel.callback = callback
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, TopActivity::class.java)
    }
}