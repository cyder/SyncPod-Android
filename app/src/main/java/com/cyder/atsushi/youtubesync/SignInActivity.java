package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.cyder.atsushi.youtubesync.json_data.User;
import com.cyder.atsushi.youtubesync.server.SignIn;
import com.cyder.atsushi.youtubesync.server.SignInInterface;

/**
 * Created by atsushi on 2017/10/26.
 */

public class SignInActivity extends AppCompatActivity
        implements SignInInterface {
    private Snackbar snackbar;
    private InputMethodManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SignIn signin = new SignIn();
        signin.setListener(this);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sign_in_tool_bar);
        toolbar.setTitle(R.string.login_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final EditText emailForm = (EditText) findViewById(R.id.sign_in_email);
        final EditText passwordForm = (EditText) findViewById(R.id.sign_in_password);

        ((Button) findViewById(R.id.sign_in_submit))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = emailForm.getText().toString();
                        String password = passwordForm.getText().toString();
                        signin.post(email, password);
                    }
                });
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        snackbar = Snackbar.make(findViewById(R.id.sign_in_view), R.string.login_mistook, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
    }

    @Override
    public void onSignedIn(User user) {
        Intent intent = new Intent();
        intent.putExtra("access_token", user.access_token);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSignInFailed() {
        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        clearText(R.id.sign_in_email);
        clearText(R.id.sign_in_password);
        snackbar.show();
    }

    private void clearText(final int editTextId){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText editText = (EditText)findViewById(editTextId);
                editText.getEditableText().clear();
            }
        });
    }
}
