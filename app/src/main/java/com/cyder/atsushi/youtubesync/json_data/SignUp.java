package com.cyder.atsushi.youtubesync.json_data;

/**
 * Created by atsushi on 2017/10/27.
 */

public class SignUp extends JsonParameter {
    public User user;

    public SignUp(String email, String name, String password) {
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
