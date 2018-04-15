package com.cyder.atsushi.youtubesync.viewmodel

import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/11.
 */

class VideoFragmentViewModel @Inject constructor(
        private val navigator: Navigator,
        private val roomRepository: RoomRepository,
        private val videoRepository: VideoRepository
) : FragmentViewModel() {
    lateinit var youtubeFragment: YouTubePlayerSupportFragment
    override fun onStart() {
        val key = roomRepository.developerKey.blockingFirst()
        val listener = videoRepository.playerState.blockingFirst()
        youtubeFragment.initialize(key, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    player?.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
                    player?.setPlayerStateChangeListener(listener)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, error: YouTubeInitializationResult?) {
            }
        })
    }

    override fun onResume() {

    }

    override fun onPause() {
    }

    override fun onStop() {
    }

}