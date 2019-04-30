package com.cyder.android.syncpod.viewmodel

import android.content.res.Resources
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.model.Video
import com.cyder.android.syncpod.repository.VideoRepository
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.FragmentViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */

class PlayListFragmentViewModel @Inject constructor(
        private val repository: VideoRepository,
        private val navigator: Navigator
) : FragmentViewModel() {
    lateinit var resources: Resources
    private val onPauseSubject = PublishSubject.create<Unit>()
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
        onPauseSubject.onNext(INVOCATION)
    }

    override fun onStop() {
    }

    fun searchVideo() = navigator.navigateToSearchVideoActivity()

    private fun convertToViewModel(videos: List<Video>): List<VideoViewModel> {
        return videos.map { VideoViewModel(repository, ObservableField(it)) }
    }

    companion object {
        val INVOCATION = Unit
    }
}