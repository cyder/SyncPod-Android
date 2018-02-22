package com.cyder.atsushi.youtubesync.api.response

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

/**
 * Created by chigichan24 on 2018/01/18.
 */

data class Video(
        val id: Int,
        @SerializedName("youtube_video_id") val youtubeVideoId: String,
        val title: String?,
        val thumbnail: Bitmap?,
        @SerializedName("thumbnail_url") val thumbnailUrl: String?,
        val duration: String?,
        val description: String?,
        @SerializedName("channel_title") val channelTitle: String?,
        @SerializedName("video_start_time") val videoStartTime: String?,
        @SerializedName("current_time") val currentTime: Int?,
        val published: String?,
        @SerializedName("view_count") val viewCount: String?
)