package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles,contents;
    TextView title;

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
        title = findViewById(R.id.titleText);

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

    @Override
    protected void onStart() {
        super.onStart();
        set_heading_font();
    }

    // set the font family of the heading title
    public void set_heading_font() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String font_family = sharedPreferences.getString("heading_font","default");
        Typeface tf = ResourcesCompat.getFont(this, R.font.maven);
        switch (font_family) {
            case "roboto":
                 tf = ResourcesCompat.getFont(this, R.font.roboto_mono);
                 break;
            case "quicksand":
                tf = ResourcesCompat.getFont(this, R.font.quicksand);
                break;
            case "amatic":
                tf = ResourcesCompat.getFont(this, R.font.amatic);
                break;
            case "bebas":
                tf = ResourcesCompat.getFont(this, R.font.bebas);
                break;
            case "helvetica":
                tf = ResourcesCompat.getFont(this, R.font.helvetica);
                break;
        }
        title.setTypeface(tf);
    }

    public void setup_recyclerview() {

        NoteListAdapter noteListAdapter = new NoteListAdapter(MainActivity.this, titles, contents);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}