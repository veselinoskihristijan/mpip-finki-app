package com.example.hristijan.tabs2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import com.example.hristijan.tabs2.dummy.TeacherContent;
import com.example.hristijan.tabs2.TeacherFragment;
import com.example.hristijan.tabs2.items.Staff;

public class TeachingStaffActivity extends FragmentActivity implements TeacherFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_staff);

//        if (findViewById(R.id.fragment_container) != null) {
//
//            // However, if we're being restored from a previous state,
//            // then we don't need to do anything and should return or else
//            // we could end up with overlapping fragments.
//            if (savedInstanceState != null) {
//                return;
//            }
//
//            // Create a new Fragment to be placed in the activity layout
//            TeacherFragment firstFragment = new TeacherFragment();
//
//            // In case this activity was started with special instructions from an
//            // Intent, pass the Intent's extras to the fragment as arguments
//            firstFragment.setArguments(getIntent().getExtras());
//
//            // Add the fragment to the 'fragment_container' FrameLayout
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, firstFragment).commit();
//        }



    }


    public void showTeacherDetails() {
        Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Staff item) {
        //Toast.makeText(TeachingStaffActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
        showTeacherDetails();
    }
}
