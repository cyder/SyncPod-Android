package com.example.atsushi.youtubesync;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import com.example.atsushi.youtubesync.json_data.User;
import com.example.atsushi.youtubesync.server.SignUp;
import com.example.atsushi.youtubesync.server.SignUpInterface;

/**
 * Created by chigichan24 on 2017/11/01.
 */

public class SignUpActivity extends AppCompatActivity implements SignUpInterface {

    private final int SIGN_IN_REQUEST_CODE = 100;
    private SignUpActivity self = this;
    private static String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sign_up_tool_bar);
        toolbar.setTitle(R.string.register_account_title);
        final SignUp signUp = new SignUp();
        signUp.setListener(this);
        final EditText emailForm = (EditText) findViewById(R.id.sign_up_email);
        final EditText nameForm = (EditText) findViewById(R.id.sign_up_name);
        final EditText passwordForm = (EditText) findViewById(R.id.sign_up_password);
        final EditText passwordConfirmForm = (EditText) findViewById(R.id.sign_up_password_confirm);


        findViewById(R.id.sign_up_submit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = emailForm.getText().toString();
                        String name = nameForm.getText().toString();
                        String password = passwordForm.getText().toString();
                        String passwordConfirm = passwordConfirmForm.getText().toString();
                        if (password.equals(passwordConfirm)) {
                            signUp.post(email, name, password);
                        }
                    }
                });

        findViewById(R.id.sign_up_link)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent varIntent =
                                new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivityForResult(varIntent, SIGN_IN_REQUEST_CODE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && intent != null) {
            if (requestCode == SIGN_IN_REQUEST_CODE) {
                String res = intent.getStringExtra("access_token");
                if (res != null) {
                    new Token(this).setToken(res);
                    Intent main = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(main);
                }
            }
        }
    }

    @Override
    public void onSignedUp(final User user) {
        runOnUiThread(new Runnable() {
            public void run() {
                new Token(self).setToken(user.access_token);
                Intent main = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }
}
