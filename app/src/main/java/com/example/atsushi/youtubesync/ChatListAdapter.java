package com.example.atsushi.youtubesync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atsushi.youtubesync.json_data.Chat;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/16.
 */

public class ChatListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Chat> chatList = new ArrayList<Chat>();

    public ChatListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setChatList(ArrayList<Chat> chatList) {
        this.chatList = chatList;
        notifyDataSetChanged();
    }

    public void addChat(Chat chat) {
        chatList.add(chat);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(chatList != null) {
            return chatList.size();
        }
        return 0;
    }

    @Override
    public Chat getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chatList.get(position).id;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final View convertView = layoutInflater.inflate(R.layout.chat_list_item,parent,false);
        final Chat chat = chatList.get(position);
        ((TextView)convertView.findViewById(R.id.message)).setText(chat.message);

        return convertView;
    }
}
