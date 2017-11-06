package com.example.atsushi.youtubesync;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atsushi.youtubesync.json_data.Video;
import com.example.atsushi.youtubesync.youtube.SearchingVideoHelper;
import com.example.atsushi.youtubesync.youtube.SearchInterface;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/11.
 */

public class SearchVideoActivity extends AppCompatActivity implements SearchInterface {
    private EditText youtubeSearchForm;
    private SearchingVideoHelper searchingHelper;
    private SearchAdapter adapter;
    private ListView listView;
    private View listFooter;


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

        searchingHelper = new SearchingVideoHelper(this);
        searchingHelper.setListener(this);
        listView = (ListView)findViewById(R.id.result_list);
        adapter = new SearchAdapter(SearchVideoActivity.this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video target = adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("youtube_video_id", target.youtube_video_id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                searchingHelper.next(firstVisibleItem, visibleItemCount, totalItemCount);
            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {}
        });


        youtubeSearchForm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("App", "Submit");
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchingHelper.get(youtubeSearchForm.getText().toString());
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onReceived(final ArrayList result) {
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.setVideoList(result);
                listView.setAdapter(adapter);
                if(listFooter == null) {
                    listFooter = getLayoutInflater().inflate(R.layout.search_result_video_list_footer, null);
                    listView.addFooterView(listFooter);
                }
            }
        });
    }

    @Override
    public void onLoaded(final ArrayList result) {
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.addVideoList(result);
            }
        });
    }
}
