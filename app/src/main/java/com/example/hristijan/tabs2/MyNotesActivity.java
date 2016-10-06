package com.example.hristijan.tabs2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.NoteItem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MyNotesActivity extends AppCompatActivity{

    private List<NoteItem> notesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter mAdapter;
    private DatabaseHandler db;
    private NoteAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        db = new DatabaseHandler(this);

        recyclerView = (RecyclerView) findViewById(R.id.mynotes);


        listener = new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoteItem item) {
                Intent intent = new Intent(getBaseContext(), ReadNoteActivity.class);
                intent.putExtra("note_id", item.getID());
                startActivity(intent);
                //Toast.makeText(MyNotesActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(final NoteItem item) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                db.deleteNote(item);
                                notesList.clear();
                                prepareNotesData();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MyNotesActivity.this);
                builder.setMessage("Избриши ја белешката?").setPositiveButton("ДА", dialogClickListener)
                        .setNegativeButton("НЕ", dialogClickListener).show();

                //Toast.makeText(MyNotesActivity.this, "Long Click ", Toast.LENGTH_SHORT).show();
            }
        };

        mAdapter = new NoteAdapter(notesList, listener);




        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = 5;
            }
        });
        recyclerView.setAdapter(mAdapter);

    }



    @Override
    protected void onResume() {
        super.onResume();
        notesList.clear();
        prepareNotesData();
    }


    public void prepareNotesData(){
        ArrayList<NoteItem> notes = db.getNotes();
        if(notes != null) {
            for (NoteItem n : notes) {
                notesList.add(n);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "Немате белешки", Toast.LENGTH_LONG).show();
        }
        mAdapter.notifyDataSetChanged();
    }

}

