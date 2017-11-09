package com.example.atsushi.youtubesync;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atsushi.youtubesync.app_data.ChatList;
import com.example.atsushi.youtubesync.app_data.ListInterface;
import com.example.atsushi.youtubesync.json_data.Chat;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/16.
 */

public class ChatListAdapter extends BaseAdapter implements ListInterface {
    private Context context;
    private LayoutInflater layoutInflater = null;
    private ChatList chatList;

    public ChatListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setChatList(ChatList chatList) {
        this.chatList = chatList;
        chatList.addListener(this);
        notifyDataSetChanged();
    }

    @Override
    public void updated() {
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        if (chatList == null) {
            return 0;
        }
        return chatList.size();
    }

    @Override
    public Chat getItem(int position) {
        if (chatList != null && 0 <= position && position <= getCount()) {
            return chatList.get(position);
        }
        return null;
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
        final Chat chat = chatList.get(position);

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
