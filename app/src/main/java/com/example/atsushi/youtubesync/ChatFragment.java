package com.example.atsushi.youtubesync;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.atsushi.youtubesync.json_data.Chat;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/16.
 */

public class ChatFragment extends Fragment {
    private ListView chatList;
    private ChatListAdapter adapter;
    private EditText chatForm;
    private ImageButton chatSubmit;

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
        chatList = view.findViewById(R.id.chat_list);
        chatList.setAdapter(adapter);

        chatList.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        chatList.setStackFromBottom(true);

        chatForm = view.findViewById(R.id.chat_form);
        chatSubmit = view.findViewById(R.id.chat_submit);

        chatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatForm.getText().toString();
                if(!message.equals("")) {
                    RoomActivity activity = (RoomActivity)getContext();
                    activity.sendChat(message);
                    chatForm.getEditableText().clear();
                }
            }
        });
    }

    public void initChatList(ArrayList<Chat> chats) {
        adapter.setChatList(chats);
    }

    public void addChat(Chat chat) {
        adapter.addChat(chat);
    }

}
