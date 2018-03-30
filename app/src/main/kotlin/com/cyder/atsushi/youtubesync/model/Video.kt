package com.cyder.atsushi.youtubesync.model

import android.graphics.Bitmap

/**
 * Created by chigichan24 on 2018/03/29.
 */
data class Video(
        val id: Int,
        val youtubeVideoId: String,
        val title: String?,
        val thumbnail: Bitmap?,
        val thumbnailUrl: String?,
        val duration: String?,
        val description: String?,
        val channelTitle: String?,
        val videoStartTime: String?,
        val currentTime: Int?,
        val published: String?,
        val viewCount: String?
)