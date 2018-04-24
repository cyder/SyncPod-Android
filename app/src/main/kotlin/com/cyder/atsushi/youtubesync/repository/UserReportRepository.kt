package com.cyder.atsushi.youtubesync.repository

import android.support.annotation.CheckResult
import io.reactivex.Completable

interface UserReportRepository {
    @CheckResult
    fun sendUserReport(message: String): Completable
}