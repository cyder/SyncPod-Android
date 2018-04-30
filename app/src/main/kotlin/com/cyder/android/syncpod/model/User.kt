package com.cyder.android.syncpod.model


/**
 * Created by chigichan24 on 2018/03/29.
 */
data class User(
        val id: Int,
        val email: String?,
        val name: String,
        val accessToken: String?
)