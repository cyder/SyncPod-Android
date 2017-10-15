package com.example.atsushi.youtubesync;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.atsushi.youtubesync.json_data.Chat;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/16.
 */

public class ChatFragment extends Fragment {
    private ListView chatList;
    private ChatListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ChatListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        chatList = (ListView) view.findViewById(R.id.chat_list);
        chatList.setAdapter(adapter);
    }

    public void initChatList(ArrayList<Chat> chats) {
        adapter.setChatList(chats);
    }

    public void addChat(Chat chat) {
        adapter.addChat(chat);
    }
}
