package com.cyder.atsushi.youtubesync;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cyder.atsushi.youtubesync.app_data.RoomData;
import com.cyder.atsushi.youtubesync.app_data.RoomDataInterface;

/**
 * Created by atsushi on 2017/11/07.
 */

public class RoomInformationFragment extends Fragment implements RoomDataInterface, SwipeRefreshLayout.OnRefreshListener {
    @NonNull
    private String shareMessage = "";
    private View view;
    private RoomData roomData;
    private OnlineUsersAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
        roomData.addListener(this);
        if (adapter != null) {
            adapter.setList(roomData.getOnlineUsersList());
        }
    }

    @Override
    public void updated() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    showRoomInformation();
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new OnlineUsersAdapter(getActivity());
        if (roomData != null) {
            adapter.setList(roomData.getOnlineUsersList());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.room_information_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.view = view;
        ListView onlineUsersList = view.findViewById(R.id.online_users_list);
        onlineUsersList.setAdapter(adapter);
        View listHeader = getActivity().getLayoutInflater().inflate(R.layout.online_users_list_header, null);
        onlineUsersList.addHeaderView(listHeader, null, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_room_info);
        swipeRefreshLayout.setOnRefreshListener(this);
        showRoomInformation();
    }

    @Override
    public void onRefresh() {
        roomData.getRoomInformationToServer();
    }

    public void onPageSelected() {
        roomData.getRoomInformationToServer();
    }

    private void showRoomInformation() {
        if (view != null && roomData != null && roomData.getRoomInformation() != null) {
            TextView name = view.findViewById(R.id.room_name);
            TextView description = view.findViewById(R.id.room_description);
            TextView online_users_title = view.findViewById(R.id.online_users_title);
            name.setText(roomData.getRoomInformation().name);
            description.setText(roomData.getRoomInformation().description);
            String onlineUserTitle = String.format(getActivity().getResources().getString(R.string.online_users_title),
                    roomData.getOnlineUsersList().size());
            online_users_title.setText(onlineUserTitle);
            shareMessage = String.format(getActivity().getResources().getString(R.string.share_room_key_message),
                    roomData.getRoomInformation().name,
                    roomData.getRoomInformation().key);

            SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_room_info);
            swipeRefreshLayout.setRefreshing(false);

            view.findViewById(R.id.share_room_key_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(getActivity());
                            builder.setText(shareMessage);
                            builder.setType("text/plain");
                            builder.startChooser();
                        }
                    });
        }
    }
}
