package com.cyder.atsushi.youtubesync.api.request

/**
 * Created by atsushi on 2018/03/28.
 */

class SignUp(email: String, name: String, password: String) {
    private val user = User(email, name, password)

    data class User (
            val email: String,
            val name: String,
            val password: String
    )
}
