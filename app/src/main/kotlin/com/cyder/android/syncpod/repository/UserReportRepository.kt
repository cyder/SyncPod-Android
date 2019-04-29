package com.cyder.android.syncpod.repository

import androidx.annotation.CheckResult
import io.reactivex.Completable

interface UserReportRepository {
    @CheckResult
    fun sendUserReport(message: String): Completable
}