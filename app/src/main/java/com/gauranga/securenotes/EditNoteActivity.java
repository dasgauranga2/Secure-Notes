package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class EditNoteActivity extends AppCompatActivity {

    String title_text;
    String content_text;
    EditText new_content;
    TextView title;
    LinearLayout edit_note_layout;

    public void save_note(View view) {
        // get the new content of the note
        String new_content_text = new_content.getText().toString();
        // check if new content is empty
        if (new_content_text.length()==0) {
            Toast toast = Toasty.custom(this, "CONTENT EMPTY", R.drawable.error_icon, R.color.toast_bg_color, 500, true, true);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, (int) (new_content.getY()+new_content.getHeight())-50);
            toast.show();
            return;
        }

        try {
            // creates a new database or open an existing database
            SQLiteDatabase database = this.openOrCreateDatabase("NOTES",MODE_PRIVATE,null);
            // create a table if one already does not exists
            // update the data in the table
            database.execSQL("UPDATE test SET content = '"+new_content_text+"' WHERE title = '"+title_text+"'");
            // go back to Main Activity
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.editNoteTitleText);
        edit_note_layout = findViewById(R.id.editNoteLinearLayout);

        Intent intent = getIntent();
        title_text = intent.getStringExtra("TITLE");
        content_text = intent.getStringExtra("CONTENT");

        title.setText("Edit " + title_text);
        new_content = findViewById(R.id.newNoteText);
        new_content.setText(content_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        set_background();
        set_heading_font();
    }

    public void set_background() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String bg = sharedPreferences.getString("background","default");
        switch (bg) {
            case "blank":
                edit_note_layout.setBackgroundResource(R.color.white);
                break;
            case "galaxy":
                edit_note_layout.setBackgroundResource(R.drawable.galaxy_bg);
                break;
            case "night_sky":
                edit_note_layout.setBackgroundResource(R.drawable.night_sky_bg);
                break;
            case "northern_lights":
                edit_note_layout.setBackgroundResource(R.drawable.northern_lights_bg);
                break;
            case "city_sunset":
                edit_note_layout.setBackgroundResource(R.drawable.city_sunset_bg);
                break;
            case "city_night":
                edit_note_layout.setBackgroundResource(R.drawable.city_night_bg);
                break;
            case "forest":
                edit_note_layout.setBackgroundResource(R.drawable.forest_bg);
                break;
            case "mountain":
                edit_note_layout.setBackgroundResource(R.drawable.mountain_bg);
                break;
        }
    }

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
}