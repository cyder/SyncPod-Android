package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.EditorInfo
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.repository.YouTubeRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/23.
 */

class SearchVideoActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val videoRepository: VideoRepository,
        private val repository: YouTubeRepository
) : ActivityViewModel() {
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }
    var searchWord: ObservableField<String> = ObservableField()
    var videoViewModels: ObservableList<VideoViewModel> = ObservableArrayList()
    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    fun onBackButtonClicked() = navigator.closeActivity()

    fun onEnterKeyClicked(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val word = searchWord.get() ?: ""
            repository.getYouTubeSearch(word)
                    .map { convertToViewModel(it) }
                    .subscribe { result, _ ->
                        videoViewModels.clear()
                        videoViewModels.addAll(result)
                    }
        }
        return false
    }

    fun onScrolled(isBottom: Boolean, lastPosition: Int) {
        if (isBottom && lastPosition == videoViewModels.size - 1) {
            repository.getNextYouTubeSearch()
                    .map { convertToViewModel(it) }
                    .subscribe { result, _ ->
                        result.forEach { videoViewModels.add(it) }
                    }
        }
    }

    private fun convertToViewModel(videos: List<Video>): List<VideoViewModel> {
        return videos.map { VideoViewModel(navigator, videoRepository, ObservableField(it)) }
    }
}