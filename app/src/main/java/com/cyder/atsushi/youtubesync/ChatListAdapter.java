package com.cyder.atsushi.youtubesync;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cyder.atsushi.youtubesync.app_data.ChatList;
import com.cyder.atsushi.youtubesync.app_data.ListInterface;
import com.cyder.atsushi.youtubesync.json_data.Chat;

import java.util.ArrayList;
import java.util.Calendar;

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
        this.chatList.addListener(this);
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        });
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
        if (chatList != null) {
            return chatList.size();
        }
        return 0;
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

        TextView name = convertView.findViewById(R.id.name);
        TextView time = convertView.findViewById(R.id.time);
        if (chat.chat_type.equals("user")) {
            name.setText(chat.user.name);
            time.setText(getTime(chat.created_at));
        } else {
            name.setVisibility(View.GONE);
            time.setVisibility(View.GONE);
        }
        TextView message = convertView.findViewById(R.id.message);
        message.setText(chat.message);
        if (chat.chat_type.equals("user")) {
            message.setTextColor(context.getResources().getColor(R.color.userMessage));
        } else {
            message.setTextColor(context.getResources().getColor(R.color.systemMessage));
        }
        return convertView;
    }

    //時間を加工する場所
    private String getTime(String t) {
        Calendar current = Calendar.getInstance();

        // 時間の文字列切り出しとintへの変換(要素数と年月日　0:年、1:月、2:日、3:時、4:分、5:秒）
        String[] temp = t.split("[/ :]", 6);
        int[] time = new int[6];
        for (int i = 0; i < temp.length; i++) {
            if (i != 3) {
                time[i] = Integer.parseInt(temp[i]);
            } else { // 時差の修正
                int hour = Integer.parseInt(temp[i]);
                if (hour + 9 >= 24) {
                    time[2]++;
                    time[3] = hour + 9 - 24;
                } else {
                    time[3] = hour + 9;
                }
            }
        }

        // 時間表示の分岐
        if (current.get(Calendar.YEAR) == time[0]) {
            if (current.get(Calendar.DATE) == time[2]) {
                return String.format("%02d:%02d", time[3], time[4]);  // 日が同じ時、時間と秒のみ表示
            } else {
                return String.format("%d/%d %02d:%02d", time[1], time[2], time[3], time[4]);  // 日が違うとき、月、日、時間、秒を表示
            }
        } else {
            return String.format("%d/%d/%d %02d:%02d", time[0], time[1], time[2], time[3], time[4]);  // 年も違うとき上記に合わせ年も表示
        }
    }
}
