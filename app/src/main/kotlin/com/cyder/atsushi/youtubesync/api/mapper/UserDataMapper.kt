package com.cyder.atsushi.youtubesync.api.mapper

import com.cyder.atsushi.youtubesync.model.User
import com.cyder.atsushi.youtubesync.api.response.User as UserResponse

/**
 * Created by chigichan24 on 2018/03/29.
 */

fun List<UserResponse>.toModel(): List<User> =
        this.map {
                it.toModel()
        }

fun UserResponse.toModel(): User =
        User(
                this.id,
                this.email,
                this.name,
                this.accessToken
        )