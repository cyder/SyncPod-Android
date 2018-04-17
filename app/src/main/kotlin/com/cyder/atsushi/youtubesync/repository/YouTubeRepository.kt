package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.model.Video
import io.reactivex.Single

interface YouTubeRepository {
    fun getYouTubeSearch(keyword: String): Single<List<Video>>
}