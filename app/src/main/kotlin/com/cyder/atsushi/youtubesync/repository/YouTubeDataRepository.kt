package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import javax.inject.Inject

class YouTubeDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi,
        private val token: String
) : YouTubeRepository  {
}