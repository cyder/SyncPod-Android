package com.cyder.atsushi.youtubesync.server;

import com.cyder.atsushi.youtubesync.json_data.User;

/**
 * Created by atsushi on 2017/10/27.
 */

public interface SignUpInterface {
    public void onSignedUp(User user);
    public void onSignUpFailed();
}
