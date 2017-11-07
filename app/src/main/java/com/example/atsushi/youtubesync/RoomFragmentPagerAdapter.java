package com.example.atsushi.youtubesync;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atsushi on 2017/10/16.
 */

public class RoomFragmentPagerAdapter extends FragmentPagerAdapter {
    PlayListFragment playListFragment = new PlayListFragment();
    ChatFragment chatFragment = new ChatFragment();
    RoomInformationFragment roomInformationFragment = new RoomInformationFragment();
    List<Fragment> fragments = new ArrayList<>();

    private Resources resources;

    public RoomFragmentPagerAdapter(FragmentManager fm, Resources resources) {
        super(fm);
        this.resources = resources;
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
}
