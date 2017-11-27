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
import com.cyder.atsushi.youtubesync.json_data.Video;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chigichan24 on 2017/11/19.
 */

public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener {

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
    public void onPause() {
        super.onPause();
        stopProgressBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        updateProgressBar();
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
        if (nextVideo != null) {
            prepareVideo(nextVideo);
        } else {
            clearNowPlayingVideo();
        }
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    public void startVideo(final Video video) {
        if (player != null) {
            player.loadVideo(video.youtube_video_id, video.current_time * 1000);
        }
        setNowPlayingVideo(video);
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

    public void clearNowPlayingVideo() {
        roomData.clearNowPlayingVideo();
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                getActivity().findViewById(R.id.video_player).setVisibility(View.GONE);
            }
        });
    }

    public void prepareVideo(final Video video) {
        if (player != null) {
            player.cueVideo(video.youtube_video_id);
        }
        setNowPlayingVideo(video);
    }

    private void updateProgressBar() {
        mTimer = new Timer(true);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (player != null) {
                    double duration = (double) player.getDurationMillis();
                    if (duration > 0.0) {
                        int progress = (int) (((double) bar.getMax() * (double) player.getCurrentTimeMillis()) / duration);
                        bar.setProgress(progress);
                    }
                }
            }
        }, 0, 500);
    }

    private void stopProgressBar() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = null;
    }

}
