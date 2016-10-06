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
import android.view.View;
import android.widget.Toast;

import com.example.hristijan.tabs2.dummy.DepthPageTransformer;
import com.example.hristijan.tabs2.dummy.DummyContent;
import com.example.hristijan.tabs2.items.Lecture;

public class WeeklyScheduleActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.containerSchedule);
        tabLayout = (TabLayout)findViewById(R.id.tabsDays);

        PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("loadedSchedule", true);
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
                    return new ItemFragment(1);
                case 1:
                    return new ItemFragment(2);
                case 2:
                    return new ItemFragment(3);
                case 3:
                    return new ItemFragment(4);
                case 4:
                    return new ItemFragment(5);
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
    public void onListFragmentInteraction(Lecture item) {
        Toast.makeText(WeeklyScheduleActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
    }

    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }



}
