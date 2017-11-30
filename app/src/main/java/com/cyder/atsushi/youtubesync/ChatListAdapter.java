package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyder.atsushi.youtubesync.app_data.ChatList;
import com.cyder.atsushi.youtubesync.json_data.Chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * 1.現在時刻 & 現在タイムゾーン取得
     * 2.UTCのデートフォーマッタでチャット投稿時の時刻を表すtimeに食わせる
     * 3.デートフォーマッタのタイムゾーンを現在タイムゾーンに
     * 4.時刻解析 & 値返却
     *
     * @param t "UTC"の時間の文字列(yyyy/MM/dd HH:mm:ss)
     * @return "日本標準時"の時間文字列(投稿された時により変わる)
     */
    private String getTime(String t) {
        // このままで現在のタイムゾーンを取得できているようです
        // -- 1 --
        Calendar current = Calendar.getInstance();

        // -- 2 --
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Calendar time = Calendar.getInstance();
        sdFormat.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        try {
            time.setTime(sdFormat.parse(t));
        } catch (ParseException e) {
            System.out.println(e);
        }

        // -- 3 --
        sdFormat.setTimeZone(current.getTimeZone());

        // 時間表示の分岐
        // -- 4 --
        if (current.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
            if (current.get(Calendar.DATE) == time.get(Calendar.DATE)) {
                // 日が同じ時、時間と分のみ表示
                sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "Hm"));
            } else {
                // 日が違うとき、月、日、時間を表示
                sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "MdHm"));
            }
        } else {
            // 年も違うとき上記に合わせ年も表示
            sdFormat.applyPattern(DateFormat.getBestDateTimePattern(Locale.getDefault(), "yMdHm"));
        }

        return sdFormat.format(time.getTime());
    }
}
