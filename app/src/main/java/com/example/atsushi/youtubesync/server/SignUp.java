package com.example.atsushi.youtubesync.server;

import android.util.Log;

import com.example.atsushi.youtubesync.json_data.Result;
import com.example.atsushi.youtubesync.json_data.SignUpParam;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by atsushi on 2017/10/27.
 */

public class SignUp {
    private SignUpInterface listener = null;

    public void setListener(SignUpInterface _listener) {
        listener = _listener;
    }

    public void post(final String email, final String name, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    SignUpParam param = new SignUpParam(email, name, password);
                    URL url = new URL("http://59.106.220.89:3000/api/v1/users");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    OutputStream os = con.getOutputStream();
                    PrintStream ps = new PrintStream(os);
                    ps.print(gson.toJson(param));
                    ps.close();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String buffer = reader.readLine();
                    Result result = gson.fromJson(buffer, Result.class);
                    if(result.result != null && result.result.equals("success")) {
                        listener.onSignedUp(result.user);
                    }
                } catch(Exception e) {
                    Log.e("App", "There was an IO error: " + e.getCause() + " : " + e.getMessage());
                }
            }
        }).start();
    }
}
