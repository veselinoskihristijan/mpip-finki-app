package com.example.hristijan.tabs2;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.NoteItem;

import java.util.List;

/**
 * Created by Hristijan on 7/31/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private List<NoteItem> notesList;
    private OnItemClickListener listener;
    private DatabaseHandler db;

    public interface OnItemClickListener {
        void onItemClick(NoteItem item);

        void onLongClick(NoteItem item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content, date;
        public ImageView done;

        public MyViewHolder(final View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.my_note_title);
            content = (TextView) view.findViewById(R.id.my_note_content);
            date = (TextView) view.findViewById(R.id.my_note_datetime);
            done = (ImageView) view.findViewById(R.id.doneButton);
        }

        public void bind(final NoteItem note, final OnItemClickListener listener) {
            title.setText(note.getTitle());
            content.setText(note.getContent());
            date.setText(note.getDateParsed());
            if(note.isDone()){
                done.setImageResource(R.drawable.checked);
            } else {
                done.setImageResource(R.drawable.not_checked);
            }

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(note.isDone()){
                        note.setDone(false);
                        done.setImageResource(R.drawable.not_checked);
                    } else {
                        note.setDone(true);
                        done.setImageResource(R.drawable.checked);
                    }
                    db.updateNote(note);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(note);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(note);
                    return true;
                }
            });
        }
    }


    public NoteAdapter(List<NoteItem> notesList, OnItemClickListener listener) {
        this.notesList = notesList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note, parent, false);
        db = new DatabaseHandler(parent.getContext());
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(notesList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
