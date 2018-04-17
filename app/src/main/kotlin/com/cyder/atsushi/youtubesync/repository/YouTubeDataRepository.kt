package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Video
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Singles
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
        return Single.just(keyword)
                .filter { it.isNotBlank() }
                .toSingle()
                .doOnSuccess {
                    this.keyword.onNext(it)
                }
                .flatMap {
                    getYouTubeSearch(it, null)
                }
    }

    override fun getNextYouTubeSearch(): Single<List<Video>> {
        return Singles.zip(keyword.first(""), nextToken.first(""))
                .filter { it.first.isNotBlank() && it.second.isNotBlank() }
                .toSingle()
                .flatMap {
                    getYouTubeSearch(it.first, it.second)
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