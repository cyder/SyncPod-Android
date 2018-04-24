package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */

class PlayListFragmentViewModel @Inject constructor(
        private val repository: VideoRepository,
        private val navigator: Navigator
) : FragmentViewModel() {
    var videoViewModels: ObservableList<VideoViewModel> = ObservableArrayList()
    var nowPlayVideo: ObservableField<Video> = ObservableField()
    var isPlaying: ObservableBoolean = ObservableBoolean(false)
    var hasPlayList: ObservableBoolean = ObservableBoolean(true)

    init {
        repository.playListObservable
                .map { convertToViewModel(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    videoViewModels.clear()
                    videoViewModels.addAll(it)
                    hasPlayList.set(videoViewModels.isNotEmpty())
                }

        repository.observeNowPlayingVideo()
                .subscribe {
                    nowPlayVideo.set(it)
                }

        repository.observeIsPlaying()
                .subscribe {
                    isPlaying.set(it)
                }
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun searchVideo() = navigator.navigateToSearchVideoActivity()

    private fun convertToViewModel(videos: List<Video>): List<VideoViewModel> {
        return videos.map { VideoViewModel(repository, ObservableField(it)) }
    }
}