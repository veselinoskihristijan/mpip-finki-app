package com.example.hristijan.tabs2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.ConsultationsContent;
import com.example.hristijan.tabs2.dummy.DepthPageTransformer;
import com.example.hristijan.tabs2.dummy.DummyContent;
import com.example.hristijan.tabs2.dummy.HomeContent;
import com.example.hristijan.tabs2.items.Consultation;
import com.example.hristijan.tabs2.items.Lecture;
import com.example.hristijan.tabs2.items.News;
import com.example.hristijan.tabs2.items.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemFragment.OnListFragmentInteractionListener, ConsultationsFragment.OnListFragmentInteractionListener, HomePageFragment.OnListFragmentInteractionListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DatabaseHandler db;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(this);

        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);

        viewPager = (ViewPager)findViewById(R.id.container);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout = (TabLayout)findViewById(R.id.tabs);

//        if(savedInstanceState != null) {
//            PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
//            viewPager.setAdapter(pagerAdapter);
//            tabLayout.setupWithViewPager(viewPager);
//            setupTabIcons();
//        } else {
//            //new AsyncFetchConsultations().execute();
//        }

        pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NoteActivity.class);
                startActivity(intent);
            }
        });


        settings = getSharedPreferences("filldata", Context.MODE_PRIVATE);
        if (settings.getBoolean("subjects", true)) {
            //fillSubjectsData();
            editor = settings.edit();
            editor.putBoolean("subjects", false);
            editor.commit();
        }

        Log.i("ALLCONS: ", db.getTodaysConsultations().toString());
        Log.i("ALLLECTURESSfalse: ", db.getAllSubjects(false).toString());

        Log.i("ALLLECTURES: ", db.getLectures().toString());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        fillCourses(this, R.raw.names);

    }

    public static void fillCourses(Context context, int resourceID) {

        InputStream inputStream = context.getResources().openRawResource(resourceID);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputReader);

        String line;
        try {
            while ((line = buffreader.readLine()) != null) {
                String[] data = line.split(",");
//                String ID = data[0];
//                String NAME = data[1];
//                int SEMESTER = Integer.parseInt(data[2])
//                boolean MANDATORY = if (data[3] == "true");
//                String DESCRIPTION = data[4];
//                int ECTS = Integer.parseInt(data[5]);
//                String CURRICULUM = data[6];
                Log.i("PNAMES", data[0]);
                Log.i("PNAMES", data[1]);
                //db.addCourse(new Subject(ID, NAME, SEMESTER, MANDATORY, DESCRIPTION, ECTS, CURRICULUM));
            }
        } catch (IOException e) {
            //something
        }
    }


    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openFinkiUrl(View view) {
        Uri uri = Uri.parse("http://finki.ukim.mk/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void showStudies() {
        Intent intent = new Intent(this, StudiesActivity.class);
        startActivity(intent);
    }

    public void showTeachingStaff() {
        Intent intent = new Intent(getBaseContext(), PersonsActivity.class);
        startActivity(intent);
    }

    public void showSubjects(){
        Intent intent = new Intent(getBaseContext(), SubjectsActivity.class);
        startActivity(intent);
    }

    public void showMySubjects(){
        Intent intent = new Intent(getBaseContext(), MySubjectsActivity.class);
        startActivity(intent);
    }

    public void showWeeklySchedule(){
        Intent intent = new Intent(getBaseContext(), WeeklyScheduleActivity.class);
        startActivity(intent);
    }

    public void showWeeklyConsultations(){
        Intent intent = new Intent(getBaseContext(), WeeklyConsultationsActivity.class);
        startActivity(intent);
    }

    public void showMyNotes(){
        Intent intent = new Intent(getBaseContext(), MyNotesActivity.class);
        startActivity(intent);
    }

    public void aboutUs() {
        Intent intent = new Intent(getBaseContext(), AboutUsActivity.class);
        startActivity(intent);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.newspaper);
        tabLayout.getTabAt(1).setIcon(R.drawable.calendar_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.graduate);
    }

    @Override
     public void onListFragmentInteraction(Lecture item) {
        //Toast.makeText(NavigationDrawerActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(Consultation item) {
        //Toast.makeText(NavigationDrawerActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(Object item) {
        if(item instanceof News){
            News n = (News)item;
            Intent intent = new Intent(NavigationDrawerActivity.this, NewsContentActivity.class);
            intent.putExtra("newsID", n.getID());
            startActivity(intent);
        }
        //Toast.makeText(NavigationDrawerActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
    }


    class FixedTabsPagerAdapter extends FragmentPagerAdapter {

        private Calendar calendar;

        public FixedTabsPagerAdapter(FragmentManager fm){
            super(fm);
            calendar = Calendar.getInstance();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new HomePageFragment();
                case 1:
                    return new ItemFragment();
                case 2:
                    return new ConsultationsFragment();
                default:
                    return null;
            }
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);

        menu.getItem(0).setIcon(R.drawable.search_ic);
        menu.getItem(1).setIcon(R.drawable.settings_ic);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            Intent intent = new Intent(NavigationDrawerActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_studies) {
            showStudies();
        } else if (id == R.id.nav_teaching_staff) {
            showTeachingStaff();
        } else if (id == R.id.nav_about_us) {
            aboutUs();
        } else if (id == R.id.nav_notes) {
            showMyNotes();
        } else if (id == R.id.nav_subjects) {
            showSubjects();
        } else if (id == R.id.nav_my_subjects) {
            showMySubjects();
        } else if (id == R.id.nav_my_schedule) {
            showWeeklySchedule();
        } else if (id == R.id.nav__my_consultations) {
            showWeeklyConsultations();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rate) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("loadedPage", true);
        super.onSaveInstanceState(outState);
    }

//    private class AsyncFetchConsultations extends AsyncTask<String, String, String> {
//        public static final int CONNECTION_TIMEOUT = 10000;
//        public static final int READ_TIMEOUT = 15000;
//
//        ProgressDialog pdLoading;
//        HttpURLConnection conn;
//        ArrayList<HttpURLConnection> connections = new ArrayList<>();
//        ArrayList<URL> urls = new ArrayList<>();
//        URL url = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            //this method will be running on UI thread
////            pdLoading.setMessage("\tLoading...");
////            pdLoading.setCancelable(false);
////            pdLoading.show();
//            pdLoading = ProgressDialog.show(NavigationDrawerActivity.this, "Превземање на содржината...", "Ве молиме, почекајте...", true);
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            ArrayList<String> results = new ArrayList<>();
//
//            try {
//
//                urls.add(new URL("http://pastebin.com/raw/CYN8dA5Y"));
//                urls.add(new URL("http://pastebin.com/raw/CYN8dA5Y"));
//
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return e.toString();
//            }
//
//                try {
//                    connections.add((HttpURLConnection) urls.get(0).openConnection());
//                    connections.add((HttpURLConnection) urls.get(1).openConnection());
//
//                    for (HttpURLConnection c : connections) {
//                        c.setReadTimeout(READ_TIMEOUT);
//                        c.setConnectTimeout(CONNECTION_TIMEOUT);
//                        c.setRequestMethod("GET");
//                        c.setDoOutput(true);
//                    }
//                } catch (Exception excep){
//                    excep.printStackTrace();
//                    return excep.toString();
//                }
//
//
//            try {
//
//                int response_code;
//
//
//
//                for(HttpURLConnection c : connections){
//
//                    response_code = c.getResponseCode();
//
//                    if (response_code == HttpURLConnection.HTTP_OK) {
//                        InputStream input = conn.getInputStream();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                        StringBuilder result = new StringBuilder();
//                        String line;
//
//                        while ((line = reader.readLine()) != null) {
//                            result.append(line);
//                        }
//
//                        results.add(result.toString());
//
//                    } else {
//                        return ("unsuccessful");
//                    }
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.toString();
//            } finally {
//                return (results.toString());
//                conn.disconnect();
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            //this method will be running on UI thread
//
//            pdLoading.dismiss();
//
//            //List<DataFish> data=new ArrayList<>();
//
//            pdLoading.dismiss();
//            try {
//
//                JSONArray jArray = new JSONArray(result);
//
//                ConsultationsContent.ITEMS.clear();
//
//                // Extract data from json and store into ArrayList as class objects
//                for(int i=0;i<jArray.length();i++){
//                    JSONObject json_data = jArray.getJSONObject(i);
//                    //ConsultationsContent.ConsultationItem consultation = new ConsultationsContent.ConsultationItem();
//                    //consultation.profImage= json_data.getString("fish_img");
//
////                    consultation.profName= json_data.getString("prof_name");
////                    consultation.location= json_data.getString("location");
////                    consultation.time= json_data.getString("time");
//                    ConsultationsContent.ITEMS.add(new ConsultationsContent.ConsultationItem(json_data.getString("prof_name"), json_data.getString("location"), json_data.getString("time")));
//                    //ConsultationsContent.ITEMS.add(consultation);
//                    //data.add(consultation);
//                }
//
//                PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
//                viewPager.setAdapter(pagerAdapter);
//                tabLayout.setupWithViewPager(viewPager);
//                setupTabIcons();
//
//                // Setup and Handover data to recyclerview
//
////                mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
////                mAdapter = new AdapterFish(MainActivity.this, data);
////                mRVFishPrice.setAdapter(mAdapter);
////                mRVFishPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//            } catch (JSONException e) {
//                Toast.makeText(NavigationDrawerActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//    }
}
