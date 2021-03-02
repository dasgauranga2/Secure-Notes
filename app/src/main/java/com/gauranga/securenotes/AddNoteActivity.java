package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText title,content;

    public void save_note(View view) {
        // get the title and content entered by the user
        String title_text = title.getText().toString();
        String content_text = content.getText().toString();

        // check if the title/content is empty
        if (title_text.length()==0) {
            Toast.makeText(this, "TITLE EMPTY", Toast.LENGTH_SHORT).show();
            return;
        }
        if (content_text.length()==0) {
            Toast.makeText(this, "CONTENT EMPTY", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // creates a new database or open an existing database
            SQLiteDatabase database = this.openOrCreateDatabase("NOTES",MODE_PRIVATE,null);
            // create a table if one already does not exists
            // and specify the fields of the table and their types
            database.execSQL("CREATE TABLE IF NOT EXISTS test (title VARCHAR, content VARCHAR, UNIQUE (title))");
            // insert data into the table
            database.execSQL("INSERT INTO test (title,content) VALUES ('"+title_text+"','"+content_text+"')");
            // go back to Main Activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        catch (Exception e) {
            String error_message = e.toString().toLowerCase();
            if (error_message.contains("unique") && error_message.contains("constraint")) {
                Toast.makeText(this, "ENTER A DIFFERENT NOTE TITLE", Toast.LENGTH_SHORT).show();
            }
            Log.i("DB_ERROR", error_message);
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.titleInputText);
        content = findViewById(R.id.contentInputText);
    }
}