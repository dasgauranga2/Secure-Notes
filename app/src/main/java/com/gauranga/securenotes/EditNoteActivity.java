package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {

    String title_text;
    String content_text;
    EditText new_content;
    TextView title;

    public void save_note(View view) {

        String new_content_text = new_content.getText().toString();

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