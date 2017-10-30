package com.example.atsushi.youtubesync.server;

import com.example.atsushi.youtubesync.json_data.Response;

/**
 * Created by atsushi on 2017/10/27.
 */

public class SignIn extends Post {
    private SignInInterface listener = null;

    public SignIn() {
        super();
    }

    public void setListener(SignInInterface _listener) {
        listener = _listener;
    }

    public void post(final String email, final String password) {
        super.post(new com.example.atsushi.youtubesync.json_data.SignIn(email, password), "login", new PostCallback() {
            @Override
            public void call(Response response) {
                listener.onSignedIn(response.user);
            }
        });
    }
}
