package com.cyder.atsushi.youtubesync.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by chigichan24 on 2018/01/18.
 */

data class User(
        val id: Int,
        val email: String?,
        val name: String,
        @SerializedName("access_token") val accessToken: String?
)