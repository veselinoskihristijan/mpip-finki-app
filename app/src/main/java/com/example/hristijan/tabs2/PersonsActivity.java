package com.example.hristijan.tabs2;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hristijan.tabs2.dummy.ConsultationsContent;
import com.example.hristijan.tabs2.dummy.DepthPageTransformer;
import com.example.hristijan.tabs2.dummy.TeacherContent;
import com.example.hristijan.tabs2.dummy.TeacherContent.TeacherItem;
import com.example.hristijan.tabs2.items.Staff;

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
import java.util.List;

public class PersonsActivity extends AppCompatActivity implements TeacherFragment.OnListFragmentInteractionListener{

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if(savedInstanceState != null) {
//            PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
//            viewPager.setAdapter(pagerAdapter);
//            tabLayout.setupWithViewPager(viewPager);
//        } else {
//            new AsyncFetchPersons().execute();
//        }

        viewPager = (ViewPager)findViewById(R.id.containerPersons);
        tabLayout = (TabLayout)findViewById(R.id.tabsPersons);

        PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("loaded", true);
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
                    return new TeacherFragment("1");
                case 1:
                    return new TeacherFragment("2");
                case 2:
                    return new TeacherFragment("3");
                case 3:
                    return new TeacherFragment("4");
                case 4:
                    return new TeacherFragment("5");
                default:
                    return null;
            }
        }

        String titles[] = new String[]{"ПРОФЕСОРИ", "ДОЦЕНТИ", "АСИСТЕНТИ", "СОРАБОТНИЦИ", "ДЕМОНСТРАТОРИ"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }



    @Override
    public void onListFragmentInteraction(Staff item) {
        //Toast.makeText(PersonsActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
        intent.putExtra("staffKey", item.getEmail());
        startActivity(intent);
    }

//    private class AsyncFetchPersons extends AsyncTask<String, String, String> {
//        public static final int CONNECTION_TIMEOUT = 10000;
//        public static final int READ_TIMEOUT = 15000;
//
//        ProgressDialog pdLoading;
//        HttpURLConnection conn;
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
//            pdLoading = ProgressDialog.show(PersonsActivity.this, "Loading...", "Please wait...", true);
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//                // Enter URL address where your json file resides
//                // Even you can make call to php file which returns json data
//                url = new URL("http://pastebin.com/raw/UZSafPEc");
//
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return e.toString();
//            }
//            try {
//
//                // Setup HttpURLConnection class to send and receive data from php and mysql
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(READ_TIMEOUT);
//                conn.setConnectTimeout(CONNECTION_TIMEOUT);
//                conn.setRequestMethod("GET");
//
//                // setDoOutput to true as we recieve data from json file
//                conn.setDoOutput(true);
//
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//                return e1.toString();
//            }
//
//            try {
//
//                int response_code = conn.getResponseCode();
//
//                // Check if successful connection made
//                if (response_code == HttpURLConnection.HTTP_OK) {
//
//                    // Read data sent from server
//                    InputStream input = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//
//                    // Pass data to onPostExecute method
//                    return (result.toString());
//
//                } else {
//
//                    return ("unsuccessful");
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.toString();
//            } finally {
//                conn.disconnect();
//            }
//
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
//
//            TeacherContent.PERSONS_MAP.clear();
//
//            try {
//
//                JSONArray jArray = new JSONArray(result);
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
//                    if(TeacherContent.PERSONS_MAP.get(json_data.getString("person_level")) == null){
//                        TeacherContent.PERSONS_MAP.put(json_data.getString("person_level"), new ArrayList<TeacherItem>());
//                    }
//                    String nameParts[] = json_data.getString("person_name").split(" ");
//                    TeacherContent.PERSONS_MAP.get(json_data.getString("person_level")).add(new TeacherContent.TeacherItem(json_data.getString("person_id"), nameParts[0], nameParts[1]));
//                    //ConsultationsContent.ITEMS.add(consultation);
//                    //data.add(consultation);
//                    Log.i("PERSON", json_data.getString("person_level") + " " + nameParts[0] + " " + nameParts[1]);
//                }
//
//                Log.d("PROFESORI", TeacherContent.PERSONS_MAP.toString());
//
//
//                // Setup and Handover data to recyclerview
//
//                PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
//                viewPager.setAdapter(pagerAdapter);
//
//                tabLayout.setupWithViewPager(viewPager);
//
////                mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
////                mAdapter = new AdapterFish(MainActivity.this, data);
////                mRVFishPrice.setAdapter(mAdapter);
////                mRVFishPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//            } catch (JSONException e) {
//                Toast.makeText(PersonsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//    }

}
