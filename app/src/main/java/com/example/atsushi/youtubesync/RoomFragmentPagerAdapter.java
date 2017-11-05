package com.example.atsushi.youtubesync;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by atsushi on 2017/10/16.
 */

public class RoomFragmentPagerAdapter extends FragmentPagerAdapter {
    PlayListFragment playListFragment = new PlayListFragment();
    ChatFragment chatFragment = new ChatFragment();
    List<Fragment> fragments;

    public RoomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(playListFragment);
        fragments.add(chatFragment);
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
                return ((PlayListFragment) fragment).getTitle();
            }
            if (fragment instanceof ChatFragment) {
                return ((ChatFragment) fragment).getTitle();
            }
        }
        return null;
    }
}
