package com.cyder.atsushi.youtubesync.websocket

import com.google.gson.annotations.SerializedName


/**
 * Created by chigichan24 on 2018/04/15.
 */

data class Response (
        @SerializedName("data_type") val dataType: String,
        val data: Data
)