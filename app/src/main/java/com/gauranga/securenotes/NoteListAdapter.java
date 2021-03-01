package com.gauranga.securenotes;
//
//public class NoteListAdapter {
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {

    List<String> titles;
    List<String> contents;
    Context context;

    // the context and the data is passed to the adapter
    public NoteListAdapter(Context ct, List<String> tit, List<String> cont) {
        context = ct;
        titles = tit;
        contents = cont;
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
        holder.title.setText(titles.get(position));

        // detect if an item is clicked
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, titles.get(position), Toast.LENGTH_SHORT).show();
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