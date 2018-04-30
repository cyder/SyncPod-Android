package com.cyder.android.syncpod.repository

import com.cyder.android.syncpod.model.Video
import io.reactivex.Single

interface YouTubeRepository {
    fun getYouTubeSearch(keyword: String): Single<List<Video>>
    fun getNextYouTubeSearch(): Single<List<Video>>
}