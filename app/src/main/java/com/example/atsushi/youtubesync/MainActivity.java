package com.example.atsushi.youtubesync;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.atsushi.youtubesync.server.MySelf;

public class MainActivity extends AppCompatActivity {

    final int signInRequestCode = 100;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("youtube-sync", MODE_PRIVATE);
        String token = pref.getString("access_token", "");
        if(!token.equals("")) {
            MySelf.singIn(token);
        }

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
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar)findViewById(R.id.sign_up_tool_bar);
        toolbar.setTitle("アカウント登録");

        ((Button) findViewById(R.id.sign_up_link))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent varIntent =
                                new Intent(MainActivity.this, SignInActivity.class);
                        startActivityForResult(varIntent, signInRequestCode);
                    }
                });
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == RESULT_OK && requestCode == signInRequestCode && null != intent) {
            String res = intent.getStringExtra("access_token");
            if(res != null) {
                setToken(res);
            }
        }
    }

    private void setToken(String token) {
        MySelf.singIn(token);
        startMainActivity();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("access_token", token);
        editor.commit();
    }
}
