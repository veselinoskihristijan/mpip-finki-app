package com.example.hristijan.tabs2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.hristijan.tabs2.dummy.SubjectsContent;
import com.example.hristijan.tabs2.items.Subject;

public class SubjectsActivity extends AppCompatActivity implements SubjectFragment.OnListFragmentInteractionListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewPagerSubjects);
        tabLayout = (TabLayout)findViewById(R.id.tabsSubjects);
        PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    class FixedTabsPagerAdapter extends FragmentPagerAdapter {

        public FixedTabsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new SubjectFragment(true);
                case 1:
                    return new SubjectFragment(false);
                default:
                    return null;
            }
        }

        String titles[] = new String[]{"ЗАДОЛЖИТЕЛНИ", "ИЗБОРНИ"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public void onListFragmentInteraction(Subject item) {
//        if(Object instanceof Subject) {
//            Subject subject = (Subject) item;
            //Toast.makeText(SubjectsActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
//        }
//        else if(Object instanceof String) {
//            String semester = (String) item;
//            Toast.makeText(SubjectsActivity.this, semester, Toast.LENGTH_SHORT).show();
//        }

    }

}
