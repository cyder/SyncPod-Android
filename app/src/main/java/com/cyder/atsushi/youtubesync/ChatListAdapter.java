package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyder.atsushi.youtubesync.app_data.ChatList;
import com.cyder.atsushi.youtubesync.json_data.Chat;

/**
 * Created by atsushi on 2017/10/16.
 */

public class ChatListAdapter extends BaseListAdapter<Chat, ChatList> {
    public ChatListAdapter(Context context) {
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
        final View convertView = layoutInflater.inflate(R.layout.chat_list_item, parent, false);
        final Chat chat = list.get(position);

        TextView message = convertView.findViewById(R.id.message);
        message.setText(chat.message);
        if (chat.chat_type.equals("user")) {
            message.setTextColor(context.getResources().getColor(R.color.userMessage));
        } else {
            message.setTextColor(context.getResources().getColor(R.color.systemMessage));
        }
        return convertView;
    }
}
