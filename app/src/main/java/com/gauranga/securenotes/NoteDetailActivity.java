package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class NoteDetailActivity extends AppCompatActivity {

    TextView title,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        title = findViewById(R.id.noteDetailTitleText);
        content = findViewById(R.id.noteDetailContentText);

        Intent intent = getIntent();
        String title_text = intent.getStringExtra("TITLE");
        String content_text = intent.getStringExtra("CONTENT");

        title.setText(title_text);
        content.setText(content_text);
    }
}