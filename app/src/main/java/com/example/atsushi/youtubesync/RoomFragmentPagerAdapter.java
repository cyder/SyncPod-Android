package com.example.atsushi.youtubesync;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by atsushi on 2017/10/16.
 */

public class RoomFragmentPagerAdapter extends FragmentPagerAdapter {
    PlayListFragment playListFragment = new PlayListFragment();
    ChatFragment chatFragment = new ChatFragment();
    public RoomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return playListFragment;
            case 1:
                return chatFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "プレイリスト";
            case 1:
                return "チャット";
        }
        return null;
    }
}
