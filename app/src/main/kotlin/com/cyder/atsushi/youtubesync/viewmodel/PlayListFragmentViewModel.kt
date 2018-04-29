package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */

class PlayListFragmentViewModel @Inject constructor(
        private val repository: VideoRepository,
        private val navigator: Navigator
) : FragmentViewModel() {
    val videoViewModels: ObservableList<VideoViewModel> = ObservableArrayList()
    val nowPlayVideo: ObservableField<Video> = ObservableField()
    val published: ObservableField<String> = ObservableField()
    val viewCount: ObservableField<String> = ObservableField()
    val isPlaying: ObservableBoolean = ObservableBoolean(false)
    val hasPlayList: ObservableBoolean = ObservableBoolean(true)

    override fun onStart() {
    }

    override fun onResume() {
        repository.playListObservable
                .takeUntil(onPauseSubject.toFlowable(BackpressureStrategy.LATEST))
                .map { convertToViewModel(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    videoViewModels.clear()
                    videoViewModels.addAll(it)
                    hasPlayList.set(videoViewModels.isNotEmpty())
                }

        Flowable.merge(
                repository.observePrepareVideo(),
                repository.observeNowPlayingVideo()
        )
                .takeUntil(onPauseSubject.toFlowable(BackpressureStrategy.LATEST))
                .subscribe {
                    nowPlayVideo.set(it)
                    published.set(resources.getString(R.string.published).format(it.published))
                    viewCount.set(resources.getString(R.string.view_count).format(it.viewCount))
                }

        repository.observeIsPlaying()
                .takeUntil(onPauseSubject.toFlowable(BackpressureStrategy.LATEST))
                .subscribe {
                    isPlaying.set(it)
                }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
    }

    fun searchVideo() = navigator.navigateToSearchVideoActivity()

    private fun convertToViewModel(videos: List<Video>): List<VideoViewModel> {
        return videos.map { VideoViewModel(repository, ObservableField(it)) }
    }
}