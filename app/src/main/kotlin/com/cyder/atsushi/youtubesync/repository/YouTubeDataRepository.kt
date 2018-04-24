package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.util.CallSequenceException
import com.cyder.atsushi.youtubesync.util.NotFilledFormsException
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
    private var nextToken: BehaviorSubject<String>? = null
    private var keyword: BehaviorSubject<String> = BehaviorSubject.create()
    private var prevNextToken: String? = null

    override fun getYouTubeSearch(keyword: String): Single<List<Video>> {
        nextToken?.apply {
            this.onComplete()
        }
        nextToken = BehaviorSubject.create()
        prevNextToken = null
        return Single.create<String> { emitter ->
            if (keyword.isNotBlank()) {
                emitter.onSuccess(keyword)
            } else {
                emitter.onError(NotFilledFormsException())
            }
        }
                .flatMap {
                    getYouTubeSearch(it, null)
                }
                .doOnSuccess {
                    this.keyword.onNext(keyword)
                }
    }

    override fun getNextYouTubeSearch(): Single<List<Video>> {
        return Singles.zip(
                keyword.firstOrError(),
                nextToken?.firstOrError() ?: Single.error(CallSequenceException())
        )
                .flatMap {
                    if (prevNextToken == it.second) {
                        Single.error(CallSequenceException())
                    } else {
                        prevNextToken = it.second
                        getYouTubeSearch(it.first, it.second)
                    }
                }
    }

    private fun getYouTubeSearch(keyword: String, pageToken: String?): Single<List<Video>> {
        return syncPodApi.getYouTubeSearch(token, keyword, pageToken)
                .doOnSuccess {
                    it.nextPageToken?.apply {
                        nextToken?.onNext(this)
                    } ?: apply {
                        nextToken?.onComplete()
                    }
                }
                .map { it.items }
                .map { it.toModel() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}