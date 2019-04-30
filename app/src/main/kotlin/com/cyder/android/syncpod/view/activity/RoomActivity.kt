package com.cyder.android.syncpod.view.activity

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.ActivityRoomBinding
import com.cyder.android.syncpod.view.fragment.ChatFragment
import com.cyder.android.syncpod.view.fragment.PlayListFragment
import com.cyder.android.syncpod.view.fragment.RoomInfoFragment
import com.cyder.android.syncpod.viewmodel.RoomActivityViewModel
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
        val adapter = PagerAdapter()
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(OnPageChangeListener(adapter))
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
            return when (fragments[position]) {
                is PlayListFragment -> {
                    getString(R.string.playlist_title)
                }
                is ChatFragment -> {
                    getString(R.string.chat_title)
                }
                is RoomInfoFragment -> {
                    getString(R.string.room_information_title)
                }
                else -> {
                    null
                }
            }
        }

    }

    inner class OnPageChangeListener(val adapter: FragmentPagerAdapter) : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            viewModel.onPageSelected(adapter.getItem(position))
        }
    }

    companion object {
        const val ROOM_KEY = "room_key"
        fun createIntent(context: Context, roomKey: String): Intent = Intent(context, RoomActivity::class.java).putExtra(ROOM_KEY, roomKey)
    }
}