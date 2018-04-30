package com.cyder.android.syncpod.repository

import com.cyder.android.syncpod.api.SyncPodApi
import com.cyder.android.syncpod.util.NotFilledFormsException
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserReportDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi,
        private val token: String
) : UserReportRepository {
    override fun sendUserReport(message: String): Completable {
        return Single.create<String> { emitter ->
            if (message.isNotBlank()) {
                emitter.onSuccess(message)
            } else {
                emitter.onError(NotFilledFormsException())
            }
        }
                .flatMap { syncPodApi.sendUserReport(token, it) }
                .subscribeOn(Schedulers.computation())
                .toCompletable()
    }

}