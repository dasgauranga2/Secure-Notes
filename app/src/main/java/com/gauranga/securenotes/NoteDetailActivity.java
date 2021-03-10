package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class NoteDetailActivity extends AppCompatActivity {

    TextView title,content;
    String title_text,content_text;

    // delete the note
    public void delete_note(View view) {
        // create a new alert dialog
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.alert_triangle)
                .setTitle("Are you sure you want to delete ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            // creates a new database or open an existing database
                            SQLiteDatabase database = getApplicationContext().openOrCreateDatabase("NOTES",MODE_PRIVATE,null);
                            // execute the delete query
                            database.execSQL("DELETE FROM test WHERE title = '"+title_text+"'");
                            // go back to Main Activity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        catch (Exception e) {
                            Log.i("DELETE_ERROR", e.toString());
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("NO", null).show();
    }

    // edit the note
    public void edit_note(View view) {
        Intent intent = new Intent(getApplicationContext(), EditNoteActivity.class);
        intent.putExtra("TITLE",title_text);
        intent.putExtra("CONTENT",content_text);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        title = findViewById(R.id.noteDetailTitleText);
        content = findViewById(R.id.noteDetailContentText);
        content.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        // get the title and content of the note
        title_text = intent.getStringExtra("TITLE");
        content_text = intent.getStringExtra("CONTENT");
        // set the the title and the content
        title.setText(title_text);
        content.setText(content_text);
        // get the preference settings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // set the content text size
        int font_size = sharedPreferences.getInt("font_size",16);
        content.setTextSize(font_size);
        // set the content text weight
        boolean is_bold = sharedPreferences.getBoolean("text_weight", false);
        if (is_bold) {
            Typeface tf = ResourcesCompat.getFont(this, R.font.roboto_mono_bold);
            content.setTypeface(tf);
        }
        // set the content text underline
        boolean is_underline = sharedPreferences.getBoolean("text_underline", false);
        if (is_underline) {
            content.setPaintFlags(content.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        set_heading_font();
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