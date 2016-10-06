package com.example.hristijan.tabs2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v8.renderscript.*;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.ConsultationsContent;
import com.example.hristijan.tabs2.dummy.TeacherContent;
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

import jp.wasabeef.blurry.Blurry;
import jp.wasabeef.blurry.internal.BlurFactor;

public class UserProfileActivity extends AppCompatActivity {

    String staffKey = "";
    TextView name, email, resume;
    ImageView staffImage, staffImageBlured;
    private DatabaseHandler db;
    Staff s;
    private int idRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_profile);

        staffKey = getIntent().getStringExtra("staffKey");

        db = new DatabaseHandler(this);
        staffImage = (ImageView)findViewById(R.id.user_pp);
        staffImageBlured = (ImageView)findViewById(R.id.teacher_blur);
        name = (TextView)findViewById(R.id.personName);
        email = (TextView)findViewById(R.id.personEmail);
        resume = (TextView)findViewById(R.id.personResume);

        s = db.getStaff(staffKey);

        idRes = this.getResources().getIdentifier(s.getImageUrl(), "drawable", this.getPackageName());
        if(idRes != 0){
            staffImage.setImageResource(idRes);
            //staffImageBlured.setImageResource(idRes);
            //Blurry.with(this).capture(staffImageBlured.getRootView()).into(staffImageBlured);
        }


        name.setText(s.getFirstName() + " " + s.getLastName());
        email.setText(s.getEmail());
        resume.setText(s.getResume());

        //new AsyncFetchPersonInformations().execute();

        Log.i("USER_ID", staffKey);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendEmailButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{s.getEmail()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                startActivity(Intent.createChooser(emailIntent, "Прати e-mail до " + s.getFirstName() + " " + s.getLastName()));
            }
        });
    }



//    private class AsyncFetchPersonInformations extends AsyncTask<String, String, String> {
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
//            pdLoading = ProgressDialog.show(UserProfileActivity.this, "Loading Person Informations", "Please wait...", true);
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//                // Enter URL address where your json file resides
//                // Even you can make call to php file which returns json data
//                url = new URL("http://pastebin.com/raw/Se1budk4");
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
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            pdLoading.dismiss();
//
//            pdLoading.dismiss();
//
//            try {
//
//                JSONObject json = new JSONObject(result);
//
//                JSONObject values = json.getJSONObject(id);
//
//                //JSONObject jsonObject = new JSONObject(result);
//
//                name = (TextView)findViewById(R.id.personName);
//                email = (TextView)findViewById(R.id.personEmail);
//                resume = (TextView)findViewById(R.id.personResume);
//
//                name.setText(values.getString("name").toString());
//                email.setText(values.getString("email").toString());
//                resume.setText(values.getString("resume").toString());
//
//                //JSONArray jArray = new JSONArray(result);
//
////                JSONArray personArray = jArray;
////
//                Log.i("PersonArray", values.toString());
//
//
//
//                // Extract data from json and store into ArrayList as class objects
////                for(int i=0;i<jArray.length();i++){
////                    JSONObject json_data = jArray.getJSONObject(i);
////
////                    //Log.i("PERSON", json_data.getString("person_level") + " " + nameParts[0] + " " + nameParts[1]);
////                }
//
//                //Log.d("PROFESORI", TeacherContent.PERSONS_MAP.toString());
//
//
//                // Setup and Handover data to recyclerview
//
//
//            } catch (JSONException e) {
//                Toast.makeText(UserProfileActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//    }

}

