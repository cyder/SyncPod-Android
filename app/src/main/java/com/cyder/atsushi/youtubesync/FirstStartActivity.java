package com.cyder.atsushi.youtubesync;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cyder.atsushi.youtubesync.app_data.MySelf;

/**
 * Created by atsushi on 2017/11/02.
 */

public class FirstStartActivity extends AppCompatActivity {
    private final int SIGN_IN_REQUEST_CODE = 100;
    private final int SIGN_UP_REQUEST_CODE = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        findViewById(R.id.sign_in_link)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent varIntent =
                                new Intent(FirstStartActivity.this, SignInActivity.class);
                        startActivityForResult(varIntent, SIGN_IN_REQUEST_CODE);
                    }
                });

        findViewById(R.id.sign_up_link)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent varIntent =
                                new Intent(FirstStartActivity.this, SignUpActivity.class);
                        startActivityForResult(varIntent, SIGN_UP_REQUEST_CODE);
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && intent != null) {
            if (requestCode == SIGN_IN_REQUEST_CODE || requestCode == SIGN_UP_REQUEST_CODE) {
                String res = intent.getStringExtra("access_token");
                if (res != null) {
                    MySelf.setToken(res);
                    Intent main = new Intent(FirstStartActivity.this, MainActivity.class);
                    startActivity(main);
                    finish();
                }
            }
        }
    }
}
