package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivityRoomBinding
import com.cyder.atsushi.youtubesync.view.fragment.ChatFragment
import com.cyder.atsushi.youtubesync.view.fragment.PlayListFragment
import com.cyder.atsushi.youtubesync.view.fragment.RoomInfoFragment
import com.cyder.atsushi.youtubesync.viewmodel.RoomActivityViewModel
import javax.inject.Inject

class RoomActivity : BaseActivity() {

    @Inject lateinit var viewModel: RoomActivityViewModel
    private lateinit var binding: ActivityRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        binding.viewModel = viewModel

        viewModel.roomKey = intent.getStringExtra(ROOM_KEY)
        initViewPager()
    }

    private fun initViewPager() {
        binding.viewPager.adapter = PagerAdapter()
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    inner class PagerAdapter : FragmentPagerAdapter(this.supportFragmentManager) {
        val fragments: List<Fragment> = listOf(
            PlayListFragment.createInstance(),
            ChatFragment.createInstance(),
            RoomInfoFragment.createInstance(intent.getStringExtra(ROOM_KEY))
        )

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(fragments[position]) {
                is PlayListFragment -> {
                    getString(R.string.playlist_title)
                }
                is ChatFragment -> {
                    getString(R.string.chat_title)
                }
                is RoomInfoFragment -> {
                    getString(R.string.room_information_title)
                }
                else -> { null }
            }
        }

    }

    companion object {
        const val ROOM_KEY = "room_key"
        fun createIntent(context: Context, roomKey: String): Intent = Intent(context, RoomActivity::class.java).putExtra(ROOM_KEY, roomKey)
    }
}