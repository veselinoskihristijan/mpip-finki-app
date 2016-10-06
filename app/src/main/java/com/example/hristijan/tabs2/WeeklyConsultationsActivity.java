package com.example.hristijan.tabs2;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.hristijan.tabs2.dummy.ConsultationsContent;
import com.example.hristijan.tabs2.dummy.DepthPageTransformer;
import com.example.hristijan.tabs2.items.Consultation;

public class WeeklyConsultationsActivity extends AppCompatActivity implements ConsultationsFragment.OnListFragmentInteractionListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_consultations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.containerConsultations);
        tabLayout = (TabLayout)findViewById(R.id.tabsDays);

        PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("loadedConsultations", true);
        super.onSaveInstanceState(outState);
    }

    class FixedTabsPagerAdapter extends FragmentPagerAdapter {

        public FixedTabsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new ConsultationsFragment(1);
                case 1:
                    return new ConsultationsFragment(2);
                case 2:
                    return new ConsultationsFragment(3);
                case 3:
                    return new ConsultationsFragment(4);
                case 4:
                    return new ConsultationsFragment(5);
                default:
                    return null;
            }
        }

        String titles[] = new String[]{"ПОНЕДЕЛНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВРТОК", "ПЕТОК"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public void onListFragmentInteraction(Consultation item) {
        Toast.makeText(WeeklyConsultationsActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
    }
}
