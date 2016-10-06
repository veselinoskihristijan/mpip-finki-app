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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hristijan.tabs2.dummy.DepthPageTransformer;
import com.example.hristijan.tabs2.items.Study;

public class StudiesActivity extends AppCompatActivity implements StudyFragment.OnListFragmentInteractionListener{

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewPagerStudies);
        tabLayout = (TabLayout)findViewById(R.id.tabsStudies);

        PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout.setupWithViewPager(viewPager);

    }

    class FixedTabsPagerAdapter extends FragmentPagerAdapter {

        public FixedTabsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new StudyFragment(1);
                case 1:
                    return new StudyFragment(2);
                case 2:
                    return new StudyFragment(3);
                default:
                    return null;
            }
        }

        String titles[] = new String[]{"ДОДИПЛОМСКИ", "МАГИСТЕРСКИ", "ДОКТОРСКИ"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    @Override
    public void onListFragmentInteraction(Study item) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView= inflater.inflate(R.layout.study_info, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(item.getName());
        dialogBuilder.setIcon(R.drawable.mortarboard);

        TextView info = (TextView) dialogView.findViewById(R.id.study_info);
        info.setText(item.getDescription());

        dialogBuilder.create().show();
    }
}
