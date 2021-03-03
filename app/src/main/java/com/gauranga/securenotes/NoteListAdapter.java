package com.gauranga.securenotes;
//
//public class NoteListAdapter {
//}

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {

    List<String> titles;
    List<String> contents;
    Context context;

    Intent intent;

    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    // the context and the data is passed to the adapter
    public NoteListAdapter(Context ct, List<String> tit, List<String> cont) {
        context = ct;
        titles = tit;
        contents = cont;

        // create an executor object
        executor = ContextCompat.getMainExecutor(context);
        // when user wants to use biometric authentication
        // a biometric prompt will appear
        // setup the actions for biometric prompt
        biometricPrompt = new BiometricPrompt((FragmentActivity) context,
                executor, new BiometricPrompt.AuthenticationCallback() {
            // function is called if user exits the biometric prompt
            // or an error occurs while trying to use the biometric prompt
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
//                Toast.makeText(context,
//                        "AUTHENTICATION ERROR : " + errString, Toast.LENGTH_SHORT)
//                        .show();
            }
            // function is called if biometric authentication is successful
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // launch the new activity
                context.startActivity(intent);
            }
            // function is called if biometric authentication fails
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
//                Toast.makeText(context, "AUTHENTICATION FAILED",
//                        Toast.LENGTH_SHORT)
//                        .show();
            }
        });
        // setup the biometric prompt
        // set the the title
        // set the subtitle
        // set a button to exit the prompt
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("BIOMETRIC LOGIN")
                .setNegativeButtonText("Exit")
                .build();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // The 'row.xml' file in layout folder defines
        // the style for each row
        View view = inflater.inflate(R.layout.note_list_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // set the text view to the title of the note
        holder.title.setText(titles.get(position));

        // detect if an item is clicked
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create the intent
                intent = new Intent(context, NoteDetailActivity.class);
                intent.putExtra("TITLE", titles.get(position));
                intent.putExtra("CONTENT", contents.get(position));
                // launch the biometric authentication prompt
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        // return the length of the recycler view
        return titles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ConstraintLayout main_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitleText);
            main_layout = itemView.findViewById(R.id.row_layout);
        }
    }
}