package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Video
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class YouTubeDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi,
        private val token: String
) : YouTubeRepository {
    override fun getYouTubeSearch(keyword: String): Single<List<Video>> {
        return syncPodApi.getYouTubeSearch(token, keyword)
                .map { it.items }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}