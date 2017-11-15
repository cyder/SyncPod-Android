package com.cyder.atsushi.youtubesync.server;

import com.cyder.atsushi.youtubesync.json_data.Response;

/**
 * Created by atsushi on 2017/10/27.
 */

public class SignIn extends HttpRequestsHelper {
    private SignInInterface listener = null;

    public SignIn() {
        super();
    }

    public void setListener(SignInInterface _listener) {
        listener = _listener;
    }

    public void post(final String email, final String password) {
        super.post(new com.cyder.atsushi.youtubesync.json_data.SignIn(email, password), "login", new HttpRequestCallback() {
            @Override
            public void success(Response response) {
                listener.onSignedIn(response.user);
            }

            @Override
            public void failure() {
                listener.onSignInFailed();
            }
        });
    }
}
