package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyder.atsushi.youtubesync.app_data.OnlineUsersList;
import com.cyder.atsushi.youtubesync.json_data.User;

/**
 * Created by atsushi on 2017/11/12.
 */

public class OnlineUsersAdapter extends BaseListAdapter<User, OnlineUsersList> {
    public OnlineUsersAdapter(Context context) {
        super(context);
    }

    @Override
    public long getItemId(int position) {
        if (getItem(position) != null) {
            return getItem(position).id;
        }
        return -1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final View convertView = layoutInflater.inflate(R.layout.online_users_list_item, parent, false);
        final User user = list.get(position);
        ((TextView) convertView.findViewById(R.id.user_name)).setText(user.name);
        return convertView;
    }
}
