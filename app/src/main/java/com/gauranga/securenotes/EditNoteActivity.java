package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class EditNoteActivity extends AppCompatActivity {

    String title_text;
    String content_text;
    EditText new_content;
    TextView title;

    public void save_note(View view) {

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
            // insert data into the table
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

        Intent intent = getIntent();
        title_text = intent.getStringExtra("TITLE");
        content_text = intent.getStringExtra("CONTENT");

        title.setText(title_text);
        new_content = findViewById(R.id.newNoteText);
        new_content.setText(content_text);
    }
}