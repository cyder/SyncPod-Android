package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Video
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class YouTubeDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi,
        private val token: String
) : YouTubeRepository {
    private var nextToken: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private var keyword: BehaviorSubject<String> = BehaviorSubject.createDefault("")

    override fun getYouTubeSearch(keyword: String): Single<List<Video>> {
        this.keyword.onNext(keyword)
        return getYouTubeSearch(keyword, null)
    }

    override fun getNextYouTubeSearch(): Single<List<Video>> {
        return nextToken.first("")
                .filter { it.isNotBlank() }
                .toSingle()
                .flatMap {
                    getYouTubeSearch(keyword.blockingFirst(), it)
                }
    }

    private fun getYouTubeSearch(keyword: String, pageToken: String?): Single<List<Video>> {
        return syncPodApi.getYouTubeSearch(token, keyword, pageToken)
                .doOnSuccess {
                    it.nextPageToken?.apply {
                        nextToken.onNext(this)
                    } ?: apply {
                        nextToken.onNext("")
                    }
                }
                .map { it.items }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}