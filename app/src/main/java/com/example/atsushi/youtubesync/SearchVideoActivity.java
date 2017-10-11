package com.example.atsushi.youtubesync;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.atsushi.youtubesync.youtube.Search;

/**
 * Created by atsushi on 2017/10/11.
 */

public class SearchVideoActivity extends AppCompatActivity {
    private EditText youtubeVideoIdForm;
    private EditText youtubeSearchForm;
    private Search search = new Search();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.youtube_search_tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        youtubeSearchForm = (EditText) findViewById(R.id.youtube_search_form);

        youtubeSearchForm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("App", "Submit");
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search.get(youtubeSearchForm.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }
}
