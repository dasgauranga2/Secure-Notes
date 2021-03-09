package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class AddNoteActivity extends AppCompatActivity {

    EditText title,content;
    TextView heading;
    LinearLayout add_note_layout;

    public void save_note(View view) {
        // get the title and content entered by the user
        String title_text = title.getText().toString();
        String content_text = content.getText().toString();
        String current_date = get_current_date();

        // check if the title/content is empty
        if (title_text.length()==0) {
            Toast toast = Toasty.custom(this, "TITLE EMPTY", R.drawable.error_icon, R.color.toast_bg_color, 500, true, true);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, (int) (content.getY()+content.getHeight())-50);
            toast.show();
            return;
        }
        if (content_text.length()==0) {
            Toast toast = Toasty.custom(this, "CONTENT EMPTY", R.drawable.error_icon, R.color.toast_bg_color, 500, true, true);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, (int) (content.getY()+content.getHeight())-50);
            toast.show();
            return;
        }

        try {
            // creates a new database or open an existing database
            SQLiteDatabase database = this.openOrCreateDatabase("NOTES",MODE_PRIVATE,null);
            // create a table if one already does not exists
            // and specify the fields of the table and their types
            database.execSQL("CREATE TABLE IF NOT EXISTS test (title VARCHAR, content VARCHAR, currdate VARCHAR, UNIQUE (title))");
            // insert data into the table
            //Toast.makeText(this, current_dat, Toast.LENGTH_SHORT).show();
            database.execSQL("INSERT INTO test (title,content,currdate) VALUES ('"+title_text+"','"+content_text+"','"+current_date+"')");
            // go back to Main Activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        catch (Exception e) {
            // get the error message
            String error_message = e.toString().toLowerCase();
            // check for unique constraint error
            if (error_message.contains("unique") && error_message.contains("constraint")) {
                Toast toast = Toasty.custom(this, "ENTER DIFFERENT NOTE TITLE", R.drawable.error_icon, R.color.toast_bg_color, 500, true, true);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, (int) (content.getY()+content.getHeight())-50);
                toast.show();
            }
            Log.i("DB_ERROR", error_message);
            e.printStackTrace();
        }
    }
    // get the current date
    public String get_current_date() {
        Date current = Calendar.getInstance().getTime();
        return DateFormat.getDateInstance().format(current);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.titleInputText);
        content = findViewById(R.id.contentInputText);
        heading = findViewById(R.id.addNoteTitleText);
        add_note_layout = findViewById(R.id.addNoteLinearLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        set_heading_font();
        set_background();
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
        heading.setTypeface(tf);
    }

    public void set_background() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String bg = sharedPreferences.getString("background","default");
        switch (bg) {
            case "blank":
                add_note_layout.setBackgroundResource(R.color.white);
                break;
            case "galaxy":
                add_note_layout.setBackgroundResource(R.drawable.galaxy_bg);
                break;
            case "night_sky":
                add_note_layout.setBackgroundResource(R.drawable.night_sky_bg);
                break;
            case "northern_lights":
                add_note_layout.setBackgroundResource(R.drawable.northern_lights_bg);
                break;
            case "city_sunset":
                add_note_layout.setBackgroundResource(R.drawable.city_sunset_bg);
                break;
            case "city_night":
                add_note_layout.setBackgroundResource(R.drawable.city_night_bg);
                break;
            case "forest":
                add_note_layout.setBackgroundResource(R.drawable.forest_bg);
                break;
            case "mountain":
                add_note_layout.setBackgroundResource(R.drawable.mountain_bg);
                break;
        }
    }
}