package com.example.atsushi.youtubesync.json_data;

/**
 * Created by atsushi on 2017/10/27.
 */

public class SignUpParam {
    public User user;

    public SignUpParam(String email, String name, String password) {
        user = new User(email, name, password);
    }

    private class User {
        public String email;
        public String password;
        public String name;

        public User(String email, String name, String password) {
            this.email = email;
            this.name = name;
            this.password = password;
        }
    }
}
