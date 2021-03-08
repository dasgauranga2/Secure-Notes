package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles,contents,dates;
    EditText search;
    TextView title;
    LinearLayout search_bg;
    int y_offest;

    // search for all notes
    // that contain the search query
    public void search_note(View view) {
        // initialize the lists
        titles = new LinkedList<>();
        contents =  new LinkedList<>();
        dates =  new LinkedList<>();

        // hide the keyboard if open
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String search_query = search.getText().toString().toLowerCase();
        // check if search query is empty
        if (search_query.length()==0) {
            // delay displaying the toast message
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toasty.custom(SearchActivity.this, "SEARCH QUERY EMPTY", R.drawable.error_icon, R.color.toast_bg_color, 500, true, true);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, (int)recyclerView.getY()+recyclerView.getHeight()-100);
                    toast.show();
                }
            }, 200);
            return;
        }

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
            // 'date' column index
            int date_index = c.getColumnIndex("currdate");

            // move the cursor to the beginning
            c.moveToFirst();
            while (c != null) {
                // retrieve the title and content
                String title_lower = c.getString(title_index).toLowerCase();
                String content_lower = c.getString(content_index).toLowerCase();
                // check if the title or the content contains the search query entered by the user
                if (title_lower.contains(search_query) || content_lower.contains(search_query)) {
                    titles.add(c.getString(title_index));
                    contents.add(c.getString(content_index));
                    dates.add(c.getString(date_index));
                }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.noteSearchRecyclerView);
        search = findViewById(R.id.noteSearchText);
        title = findViewById(R.id.searchTitleText);
        search_bg = findViewById(R.id.searchLinearLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        set_heading_font();
        set_background();
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
    // set the background in the main screen
    public void set_background() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String bg = sharedPreferences.getString("background","default");
        switch (bg) {
            case "blank":
                search_bg.setBackgroundResource(R.color.white);
                break;
            case "galaxy":
                search_bg.setBackgroundResource(R.drawable.galaxy_bg);
                break;
            case "night_sky":
                search_bg.setBackgroundResource(R.drawable.night_sky_bg);
                break;
            case "northern_lights":
                search_bg.setBackgroundResource(R.drawable.northern_lights_bg);
                break;
            case "city_sunset":
                search_bg.setBackgroundResource(R.drawable.city_sunset_bg);
                break;
            case "city_night":
                search_bg.setBackgroundResource(R.drawable.city_night_bg);
                break;
            case "forest":
                search_bg.setBackgroundResource(R.drawable.forest_bg);
                break;
            case "mountain":
                search_bg.setBackgroundResource(R.drawable.mountain_bg);
                break;
        }
    }

    public void setup_recyclerview() {

        NoteListAdapter noteListAdapter = new NoteListAdapter(SearchActivity.this, titles, contents, dates);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}