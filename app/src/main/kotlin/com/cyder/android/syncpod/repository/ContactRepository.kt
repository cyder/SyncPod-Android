package com.cyder.android.syncpod.repository

import android.support.annotation.CheckResult
import io.reactivex.Completable

/**
 * Created by shikibu on 2018/05/07.
 */

interface ContactRepository {
    @CheckResult
    fun sendContact(message: String): Completable
}