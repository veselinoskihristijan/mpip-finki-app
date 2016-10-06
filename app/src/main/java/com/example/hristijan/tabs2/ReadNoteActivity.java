package com.example.hristijan.tabs2;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.NoteItem;

public class ReadNoteActivity extends AppCompatActivity {

    View decorView;
    TextView title, content;
    DatabaseHandler db;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_read_note);

        db = new DatabaseHandler(this);

        title = (TextView)findViewById(R.id.readModeNoteTitle);
        content = (TextView)findViewById(R.id.readModeNoteContent);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NoteActivity.class);
                intent.putExtra("note_id", noteId);
                startActivity(intent);
                finish();
            }
        });


        noteId = getIntent().getIntExtra("note_id", -1);

        if(noteId != -1){
            NoteItem note = db.getNote(noteId);
            title.setText(note.getTitle());
            content.setText(note.getContent());
        }




    }

//        private void hideSystemUI() {
//            // Set the IMMERSIVE flag.
//            // Set the content to appear under the system bars so that the content
//            // doesn't resize when the system bars hide and show.
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
//        }


}
