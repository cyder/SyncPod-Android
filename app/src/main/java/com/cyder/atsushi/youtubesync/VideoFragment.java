package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.cyder.atsushi.youtubesync.app_data.RoomData;
import com.cyder.atsushi.youtubesync.app_data.RoomDataInterface;
import com.cyder.atsushi.youtubesync.json_data.Video;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chigichan24 on 2017/11/19.
 */

public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener, RoomDataInterface {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private static final String TAG = Video.class.getSimpleName();
    private VideoFragmentListener listener = null;
    private RoomData roomData;
    private YouTubePlayer player;
    private ProgressBar bar;
    private Timer mTimer;

    public interface VideoFragmentListener {
        void onGetNowPlayingVideo();
    }

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
        roomData.addListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (VideoFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.video_fragment, container, false);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.view_player, youTubePlayerFragment).commit();
        try {
            ApplicationInfo info = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
            youTubePlayerFragment.initialize(info.metaData.getString("developer_key"), this);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, Arrays.toString(e.getStackTrace()));
        }
        bar = rootView.findViewById(R.id.progress_bar);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            player.setPlayerStateChangeListener(this);
        }
        this.player = player;
        listener.onGetNowPlayingVideo();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {
        Video nextVideo = roomData.getPlayList().getTopItem();
        if(nextVideo != null) {
            prepareVideo(nextVideo);
        } else {
            roomData.clearNowPlayingVideo();
            getActivity().findViewById(R.id.video_player).setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    @Override
    public void updated() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //TODO impliment
                }
            });
        }
    }

    public void startVideo(final Video video) {
        if (player != null) {
            player.loadVideo(video.youtube_video_id, video.current_time * 1000);
        }
        setNowPlayingVideo(video);
        updateProgressBar(video.current_time, getTotalTime(video.duration));
    }

    public void setNowPlayingVideo(final Video video) {
        stopProgressBar();
        roomData.setNowPlayingVideo(video);
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                getActivity().findViewById(R.id.video_player).setVisibility(View.VISIBLE);
            }
        });
    }

    public void prepareVideo(final Video video) {
        if (player != null) {
            player.cueVideo(video.youtube_video_id);
        }
        setNowPlayingVideo(video);
    }

    private void updateProgressBar(final int start, final int duration) {
        mTimer = new Timer(true);
        mTimer.schedule(new TimerTask() {
            int cnt = start*10;
            @Override
            public void run() {
                ++cnt;
                bar.setProgress((int)((120.0 * cnt)/duration));
            }
        }, 10, 10);
    }

    private void stopProgressBar(){
        if(mTimer != null) {
            mTimer.cancel();
        }
        mTimer = null;
    }

    private int getTotalTime(String duration) {
        List<String> items = Arrays.asList(duration.split(":", 0));
        if(items.size() == 3){
            return Integer.parseInt(items.get(0))*3600 + Integer.parseInt(items.get(1)) * 60 + Integer.parseInt(items.get(2));
        }
        else if (items.size() == 2){
            return Integer.parseInt(items.get(0))*60 + Integer.parseInt(items.get(1));
        }
        return 0;
    }

}
