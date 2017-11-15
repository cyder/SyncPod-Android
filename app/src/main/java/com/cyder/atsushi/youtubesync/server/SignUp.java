package com.cyder.atsushi.youtubesync.server;

import com.cyder.atsushi.youtubesync.json_data.Response;

/**
 * Created by atsushi on 2017/10/27.
 */

public class SignUp extends HttpRequestsHelper {
    private SignUpInterface listener = null;

    public void setListener(SignUpInterface _listener) {
        listener = _listener;
    }

    public SignUp() {
        super();
    }

    public void post(final String email, final String name, final String password) {
        super.post(new com.cyder.atsushi.youtubesync.json_data.SignUp(email, name, password), "users", new HttpRequestCallback() {
            @Override
            public void success(Response response) {
                listener.onSignedUp(response.user);
            }
            @Override
            public void failure(Response response) {
                //TODO implement
            }
        });
    }
}
