package com.example.atsushi.youtubesync.json_data;

/**
 * Created by atsushi on 2017/10/26.
 */

public class SignInParam {
    public SignInParam(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email;
    public String password;
}
