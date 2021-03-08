package com.gauranga.securenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class AddNoteActivity extends AppCompatActivity {

    EditText title,content;

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
    }
}