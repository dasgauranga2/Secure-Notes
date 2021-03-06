package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles,contents;

    // add a new note
    public void add_note(View view) {
        Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
        startActivity(intent);
    }

    // launch the settings screen
    public void settings(View view) {
        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.noteListRecyclerView);
        titles = new LinkedList<>();
        contents =  new LinkedList<>();

        try {
            // creates a new database or open an existing database
            SQLiteDatabase database = this.openOrCreateDatabase("NOTES",MODE_PRIVATE,null);
            // cursor is used to retrieve data from the table
            // below SQL query selects all the rows from the table
            Cursor c = database.rawQuery("SELECT * FROM test", null);
            // 'title' column index
            int title_index = c.getColumnIndex("title");
            // 'content' column index
            int content_index = c.getColumnIndex("content");

            // move the cursor to the beginning
            c.moveToFirst();
            while (c != null) {
                // retrieve data from the table using the cursor and column index
                titles.add(c.getString(title_index));
                contents.add(c.getString(content_index));
                // move to the next row
                c.moveToNext();
                setup_recyclerview();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setup_recyclerview() {

        NoteListAdapter noteListAdapter = new NoteListAdapter(MainActivity.this, titles, contents);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}