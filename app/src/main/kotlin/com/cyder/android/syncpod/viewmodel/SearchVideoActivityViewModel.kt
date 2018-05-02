package com.cyder.android.syncpod.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.cyder.android.syncpod.model.Video
import com.cyder.android.syncpod.repository.VideoRepository
import com.cyder.android.syncpod.repository.YouTubeRepository
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/23.
 */

class SearchVideoActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val videoRepository: VideoRepository,
        private val repository: YouTubeRepository
) : ActivityViewModel() {
    var searchWord: ObservableField<String> = ObservableField()
    var videoViewModels: ObservableList<SearchVideoViewModel> = ObservableArrayList()
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
        val word = searchWord.get() ?: ""
        videoViewModels.clear()
        repository.getYouTubeSearch(word)
                .map { convertToViewModel(it) }
                .subscribe({
                    videoViewModels.addAll(it)
                }, {

                })
        return false
    }

    fun onScrolled(isBottom: Boolean) {
        if (isBottom) {
            repository.getNextYouTubeSearch()
                    .map { convertToViewModel(it) }
                    .subscribe({
                        videoViewModels.addAll(it)
                    }, {

                    })

        }
    }

    private fun convertToViewModel(videos: List<Video>): List<SearchVideoViewModel> {
        return videos.map { SearchVideoViewModel(navigator, videoRepository, ObservableField(it)) }
    }
}