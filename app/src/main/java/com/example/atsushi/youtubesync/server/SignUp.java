package com.example.atsushi.youtubesync.server;

import com.example.atsushi.youtubesync.json_data.JsonParameter;
import com.example.atsushi.youtubesync.json_data.Response;

/**
 * Created by atsushi on 2017/10/27.
 */

public class SignUp extends Post {
    private SignUpInterface listener = null;

    public void setListener(SignUpInterface _listener) {
        listener = _listener;
    }

    public SignUp() {
        super();
    }

    @Override
    protected void post(JsonParameter jsonParameter, String endPoint, PostCallback callback) {
        super.post(jsonParameter, endPoint, callback);
    }

    public void post(final String email, final String name, final String password) {
        post(new com.example.atsushi.youtubesync.json_data.SignUp(email, name, password), "users", new PostCallback() {
            @Override
            public void call(Response response) {
                listener.onSignedUp(response.user);
            }
        });
    }
}
