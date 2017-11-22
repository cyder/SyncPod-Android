package com.cyder.atsushi.youtubesync;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atsushi on 2017/10/16.
 */

public class RoomFragmentPagerAdapter extends FragmentPagerAdapter {
    PlayListFragment playListFragment;
    ChatFragment chatFragment;
    RoomInformationFragment roomInformationFragment;
    List<Fragment> fragments = new ArrayList<>();
    FragmentManager fragmentManager;

    private Resources resources;

    public RoomFragmentPagerAdapter(FragmentManager fm, Resources resources, Bundle savedInstanceState) {
        super(fm);
        this.resources = resources;
        fragmentManager = fm;

        if (savedInstanceState != null) {
            playListFragment = (PlayListFragment) fragmentManager.getFragment(savedInstanceState, "playListFragment");
            chatFragment = (ChatFragment) fragmentManager.getFragment(savedInstanceState, "chatFragment");
            roomInformationFragment = (RoomInformationFragment) fragmentManager.getFragment(savedInstanceState, "roomInformationFragment");
        }
        if (playListFragment == null) {
            playListFragment = new PlayListFragment();
        }
        if (chatFragment == null) {
            chatFragment = new ChatFragment();
        }
        if (roomInformationFragment == null) {
            roomInformationFragment = new RoomInformationFragment();
        }

        fragments.add(playListFragment);
        fragments.add(chatFragment);
        fragments.add(roomInformationFragment);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments != null && 0 <= position && position < fragments.size()) {
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final Fragment fragment = getItem(position);
        if (fragment != null) {
            if (fragment instanceof PlayListFragment) {
                return resources.getString(R.string.playlist_title);
            }
            if (fragment instanceof ChatFragment) {
                return resources.getString(R.string.chat_title);
            }
            if (fragment instanceof RoomInformationFragment) {
                return resources.getString(R.string.room_information_title);
            }
        }
        return null;
    }

    public void saveInstanceState(Bundle savedInstanceState) {
        if (playListFragment.isStateSaved()) {
            fragmentManager.putFragment(savedInstanceState, "playListFragment", playListFragment);
        }
        if (chatFragment.isStateSaved()) {
            fragmentManager.putFragment(savedInstanceState, "chatFragment", chatFragment);
        }
        if (roomInformationFragment.isStateSaved()) {
            fragmentManager.putFragment(savedInstanceState, "roomInformationFragment", roomInformationFragment);
        }
    }
}
