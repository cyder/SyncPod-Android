package com.cyder.android.syncpod.repository

import android.support.annotation.CheckResult
import io.reactivex.Completable

interface UserReportRepository {
    @CheckResult
    fun sendUserReport(message: String): Completable
}