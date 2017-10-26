package com.example.atsushi.youtubesync.server;

import com.example.atsushi.youtubesync.json_data.User;

/**
 * Created by atsushi on 2017/10/25.
 */

public interface SignInInterface {
    void onSignedIn(User user);
}
