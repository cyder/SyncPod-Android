package com.cyder.android.syncpod.repository

import com.cyder.android.syncpod.api.SyncPodApi
import com.cyder.android.syncpod.api.mapper.toModel
import com.cyder.android.syncpod.model.Video
import com.cyder.android.syncpod.util.CallSequenceException
import com.cyder.android.syncpod.util.NotFilledFormsException
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
    private var lastNextToken: String? = null

    override fun getYouTubeSearch(keyword: String): Single<List<Video>> {
        nextToken?.apply {
            this.onComplete()
        }
        nextToken = BehaviorSubject.create()
        lastNextToken = null
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
                    if (lastNextToken == it.second) {
                        Single.error(CallSequenceException())
                    } else {
                        lastNextToken = it.second
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