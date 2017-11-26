package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by atsushi on 2017/11/27.
 */

public class ChatFormFragment extends Fragment {
    private View chatFormArea;
    private ChatFormFragment.ChatFormFragmentListener listener = null;

    public interface ChatFormFragmentListener {
        void onSendChat(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ChatFormFragment.ChatFormFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.chat_form_fragment, container, false);
        chatFormArea = rootView.findViewById(R.id.chat_form_area);

        final EditText chatForm = rootView.findViewById(R.id.chat_form);
        final ImageButton chatSubmit = rootView.findViewById(R.id.chat_submit);

        chatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatForm.getText().toString();
                if (!message.equals("")) {
                    listener.onSendChat(message);
                    chatForm.getEditableText().clear();
                }
            }
        });

        return rootView;
    }

    public void setChatFormArea(Fragment nowFragment) {
        if(chatFormArea != null) {
            if (nowFragment instanceof ChatFragment) {
                chatFormArea.setVisibility(View.VISIBLE);
            } else {
                chatFormArea.setVisibility(View.GONE);
            }
        }
    }
}
