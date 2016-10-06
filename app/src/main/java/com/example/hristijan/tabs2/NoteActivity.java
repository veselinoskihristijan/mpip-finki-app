package com.example.hristijan.tabs2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.NoteItem;

public class NoteActivity extends AppCompatActivity {

    EditText title, content;
    DatabaseHandler db;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupWindowAnimations();

        db = new DatabaseHandler(this);

        title = (EditText)findViewById(R.id.note_title);
        content = (EditText)findViewById(R.id.note_text);

        noteId = getIntent().getIntExtra("note_id", -1);

        if(noteId != -1){
            NoteItem note = db.getNote(noteId);
            title.setText(note.getTitle());
            content.setText(note.getContent());
        }

    }

    private void setupWindowAnimations() {
//        Slide slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
//        getWindow().setExitTransition(slide);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_done) {
            NoteItem note;
            if(title.getText().toString().isEmpty()) {
                note = new NoteItem(content.getText().toString(), content.getText().toString(), false);
            } else {
                note = new NoteItem(title.getText().toString(), content.getText().toString(), false);
            }
            if(noteId != -1) {
                note.setID(noteId);
                db.updateNote(note);
            } else {
                db.addNote(note);
            }

            Intent intent = new Intent(getBaseContext(), MyNotesActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getBaseContext(), MyNotesActivity.class);
        startActivity(intent);
        finish();
    }
}
