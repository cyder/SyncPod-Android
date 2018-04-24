package com.cyder.atsushi.youtubesync.api.mapper

import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.model.toCommaStyle
import com.cyder.atsushi.youtubesync.api.response.Video as VideoResponse

/**
 * Created by chigichan24 on 2018/03/29.
 */

fun VideoResponse.toModel(): Video =
        Video(
                this.id,
                this.youtubeVideoId,
                this.title,
                this.thumbnail,
                this.thumbnailUrl,
                this.duration,
                this.description,
                this.channelTitle,
                this.videoStartTime,
                this.currentTime,
                this.published,
                this.viewCount.toCommaStyle()
        )

fun List<VideoResponse>.toModel(): List<Video> =
        this.map {
            it.toModel()
        }