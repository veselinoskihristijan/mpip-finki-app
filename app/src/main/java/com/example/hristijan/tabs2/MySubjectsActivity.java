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
import android.widget.Toast;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.NoteItem;
import com.example.hristijan.tabs2.items.Subject;

import java.util.ArrayList;
import java.util.List;

public class MySubjectsActivity extends AppCompatActivity {

    private List<Subject> mySubjectsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MySubjectsAdapter mAdapter;
    private DatabaseHandler db;
//    private MySubjectsAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SubjectsActivity.class);
                startActivity(intent);
            }
        });


        db = new DatabaseHandler(this);

        recyclerView = (RecyclerView) findViewById(R.id.mySubjects);

        mAdapter = new MySubjectsAdapter(mySubjectsList);

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



//
//        listener = new MySubjectsAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Subject item) {
//                //Toast.makeText(MySubjectsActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(final Subject item) {
////                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        switch (which) {
////                            case DialogInterface.BUTTON_POSITIVE:
////                                //Yes button clicked
////                                db.deleteSubject(item);
////                                break;
////
////                            case DialogInterface.BUTTON_NEGATIVE:
////                                //No button clicked
////                                break;
////                        }
////                    }
////                };
////
////                AlertDialog.Builder builder = new AlertDialog.Builder(MySubjectsActivity.this);
////                builder.setMessage("Избриши го предметот?").setPositiveButton("ДА", dialogClickListener)
////                        .setNegativeButton("НЕ", dialogClickListener).show();
//
//            }
//        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySubjectsList.clear();
        prepareMySubjectsData();
    }

    public void prepareMySubjectsData(){
        ArrayList<Subject> subjects = db.getMySubjects();
        if(subjects != null) {
            for (Subject s : subjects) {
                mySubjectsList.add(s);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "Немате селектирано предмети", Toast.LENGTH_LONG).show();
        }
        mAdapter.notifyDataSetChanged();
    }

}
