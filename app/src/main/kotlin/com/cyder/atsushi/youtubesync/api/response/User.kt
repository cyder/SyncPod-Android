package com.cyder.atsushi.youtubesync.api.response

/**
 * Created by chigichan24 on 2018/01/18.
 */

data class User(
        val id: Int,
        val email: String?,
        val name: String,
        val accessToken: String?
)