package com.example.atsushi.youtubesync;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}
