package com.example.atsushi.youtubesync;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by atsushi on 2017/10/11.
 */

public class SearchVideoActivity extends AppCompatActivity {
    private EditText youtubeVideoIdForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        youtubeVideoIdForm = (EditText) findViewById(R.id.youtube_video_id_form);

        ((Button) findViewById(R.id.submit))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        if(youtubeVideoIdForm.getText() != null) {
                            String str = youtubeVideoIdForm.getText().toString();
                            intent.putExtra("youtube_video_id", str);
                        }
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }
}
