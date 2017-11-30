package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.cyder.atsushi.youtubesync.app_data.ChatList;
import com.cyder.atsushi.youtubesync.json_data.Chat;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

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

        TextView name = convertView.findViewById(R.id.name);
        TextView time = convertView.findViewById(R.id.time);
        if (chat.chat_type.equals("user")) {
            name.setText(chat.user.name);
        } else {
            name.setText(R.string.notice);
            name.setTextColor(ContextCompat.getColor(context, R.color.notice_message));
        }
        time.setText(getTime(chat.created_at));
        TextView message = convertView.findViewById(R.id.message);
        message.setText(chat.message);
        if (chat.chat_type.equals("user")) {
            message.setTextColor(ContextCompat.getColor(context, R.color.text));
        } else {
            message.setTextColor(ContextCompat.getColor(context, R.color.gray_text));
        }
        return convertView;
    }

    //時間を加工する場所
    private String getTime(String t) {
        Calendar current = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));  // 現在時刻の取得

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Calendar time = Calendar.getInstance();
        sdFormat.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        time.setTimeZone(TimeZone.getTimeZone("Europe/London")); // UTCにセット
        try {
            time.setTime(sdFormat.parse(t));
        } catch (ParseException e) {
            System.out.println(e);
        }
        sdFormat.setTimeZone(current.getTimeZone());
        time.setTimeZone(current.getTimeZone());

        // 時間表示の分岐
        if (current.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
            if (current.get(Calendar.DATE) == time.get(Calendar.DATE)) {
                sdFormat.applyPattern("HH:mm");   // 日が同じ時、時間と分のみ表示
            } else {
                sdFormat.applyPattern("MM/dd HH:mm");  // 日が違うとき、月、日、時間、秒を表示
            }
        } else {
            sdFormat.applyPattern("yyyy/MM/dd HH:mm");  // 年も違うとき上記に合わせ年も表示
        }
        return sdFormat.format(time.getTime());
    }
}
