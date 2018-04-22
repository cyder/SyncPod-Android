package com.cyder.atsushi.youtubesync.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.FragmentRoominfoBinding
import com.cyder.atsushi.youtubesync.viewmodel.RoomInfoFragmentViewModel
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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_roominfo, container, false)
        binding.viewModel = viewModel
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
}