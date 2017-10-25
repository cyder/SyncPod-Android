package com.example.atsushi.youtubesync;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.atsushi.youtubesync.json_data.SignInParam;
import com.example.atsushi.youtubesync.server.MySelf;
import com.example.atsushi.youtubesync.server.MySelfInterface;

public class MainActivity extends AppCompatActivity
    implements MySelfInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySelf.setListener(this);
        if (MySelf.exists()) {
            startMainActivity();
        } else {
            startSignInActivity();
        }
    }

    private void startMainActivity() {
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.startButton))
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent varIntent =
                            new Intent(MainActivity.this, VideoActivity.class);
                    startActivity(varIntent);
                }
            });
    }

    private void startSignInActivity() {
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar)findViewById(R.id.sign_in_tool_bar);
        toolbar.setTitle("Sing in");
        final EditText emailForm = (EditText) findViewById(R.id.sign_in_email);
        final EditText passwordForm = (EditText) findViewById(R.id.sign_in_password);

        ((Button) findViewById(R.id.sign_in_submit))
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailForm.getText().toString();
                    String password = passwordForm.getText().toString();
                    SignInParam param = new SignInParam(email, password);
                    MySelf.signIn(param);
                }
            });
    }

    @Override
    public void onReceived() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (MySelf.exists()) {
                    startMainActivity();
                }
            }
        });
    }
}
