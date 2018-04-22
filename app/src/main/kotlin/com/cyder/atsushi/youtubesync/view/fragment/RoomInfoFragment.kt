package com.cyder.atsushi.youtubesync.view.fragment

import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.FragmentRoominfoBinding
import com.cyder.atsushi.youtubesync.view.adapter.BindingHolder
import com.cyder.atsushi.youtubesync.databinding.ItemUserBinding
import com.cyder.atsushi.youtubesync.view.adapter.ObservableListAdapter
import com.cyder.atsushi.youtubesync.view.helper.setUpShareCompat
import com.cyder.atsushi.youtubesync.viewmodel.RoomInfoFragmentViewModel
import com.cyder.atsushi.youtubesync.viewmodel.UserViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/16.
 */

class RoomInfoFragment : BaseFragment() {
    private lateinit var binding: FragmentRoominfoBinding
    @Inject
    lateinit var viewModel: RoomInfoFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent.inject(this)
        bindViewModel(viewModel)
        viewModel.roomKey = arguments!!.getString(ROOM_KEY)
        viewModel.shareCompatCallback = activity?.setUpShareCompat()
        viewModel.resources = resources
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_roominfo, container, false)
        binding.viewModel = viewModel
        initRecyclerView()
        return binding.root
    }

    companion object {
        fun createInstance(roomKey: String): RoomInfoFragment {
            val bundle = Bundle()
            bundle.putString(ROOM_KEY, roomKey)
            val fragment = RoomInfoFragment()
            fragment.arguments = bundle
            return fragment
        }

        private const val ROOM_KEY = "room_key"
    }

    private fun initRecyclerView() {
        binding.onlineUserRecycler.isNestedScrollingEnabled = false
        val adapter = OnlineUsersAdapter(viewModel.userViewModels)
        binding.onlineUserRecycler.adapter = adapter
        binding.onlineUserRecycler.layoutManager = LinearLayoutManager(activity)
    }

    inner class OnlineUsersAdapter(list: ObservableList<UserViewModel>) : ObservableListAdapter<UserViewModel, BindingHolder<ItemUserBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemUserBinding> = BindingHolder(parent, R.layout.item_user)

        override fun onBindViewHolder(holder: BindingHolder<ItemUserBinding>?, position: Int) {
            val viewModel = getItem(position)
            val binding = holder?.binding
            binding?.viewModel = viewModel
        }
    }
}