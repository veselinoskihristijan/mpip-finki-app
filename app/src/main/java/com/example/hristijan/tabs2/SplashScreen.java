package com.example.hristijan.tabs2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.HomeContent;
import com.example.hristijan.tabs2.items.Lecture;
import com.example.hristijan.tabs2.items.News;

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
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences settings;
    private SharedPreferences lectures;
    private SharedPreferences.Editor editor;
    private final int SPLASH_DISPLAY_LENGHT = 5000;
    private DatabaseHandler db;
    private int version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        db = new DatabaseHandler(this);

        settings = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        lectures = getSharedPreferences("lectures", Context.MODE_PRIVATE);

        if (settings.getBoolean("intro", true)) {
            if (isNetworkAvailable()) {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SplashScreen.this, "Немате Интернет конекција!", Toast.LENGTH_LONG).show();
            }
        } else {
            new AsyncFetchNews().execute();
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class AsyncFetchScheduleVersion extends AsyncTask<String, String, String> {
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        //ProgressDialog pdLoading;
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pdLoading = ProgressDialog.show(SplashScreen.this, "", "", true);
        }

        @Override
        protected String doInBackground(String... params) {
            if (isNetworkAvailable()) {
                try {
                    // Верзија на распоред
                    url = new URL("http://pastebin.com/raw/wwrn4WPX");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return e.toString();
                }
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("GET");
                    conn.setDoOutput(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return e1.toString();
                }
                try {
                    int response_code = conn.getResponseCode();
                    if (response_code == HttpURLConnection.HTTP_OK) {
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        return (result.toString());
                    } else {
                        return ("unsuccessful");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                } finally {
                    conn.disconnect();
                }
            } else {
                return "Немате интернет конекција";
            }
        }

        @Override
        protected void onPostExecute(final String result) {

            if (isNetworkAvailable()) {
                try {
                    version = Integer.parseInt(result);
                    if(lectures.getInt("version", -1) == -1){
                        db.deleteLectures();
                        new AsyncFetchSchedule().execute();
                       // Toast.makeText(SplashScreen.this, "Превземање на распоредот!", Toast.LENGTH_LONG).show();
//                        editor = lectures.edit();
//                        editor.putInt("version", version);
//                        editor.commit();
                        Log.i("SCHEDULE", "Download");
                    } else if(lectures.getInt("version", -1) < version){
                        db.deleteLectures();
                        new AsyncFetchSchedule().execute();
                        Toast.makeText(SplashScreen.this, "Нова, верзија бр." + result +" на распоред!", Toast.LENGTH_LONG).show();
//                        editor = lectures.edit();
//                        editor.putInt("version", version);
//                        editor.commit();
                        Log.i("SCHEDULE", "Update");
                    } else {
                        //Toast.makeText(SplashScreen.this, "Нема нова верзија!", Toast.LENGTH_LONG).show();
                        Log.i("SCHEDULE", "No changes");
                    }
                } catch (Exception e) {
                    Log.i("ERROR SCHEDULE", e.toString());
                }
            }
        }
    }









    private class AsyncFetchNews extends AsyncTask<String, String, String> {
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        //ProgressDialog pdLoading;
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pdLoading = ProgressDialog.show(SplashScreen.this, "", "", true);
        }

        @Override
        protected String doInBackground(String... params) {

            if (isNetworkAvailable()) {
                try {
                    url = new URL("http://pastebin.com/raw/VdVwwHGY");
                    new AsyncFetchScheduleVersion().execute();
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return e.toString();
                }
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("GET");

                    conn.setDoOutput(true);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return e1.toString();
                }

                try {

                    int response_code = conn.getResponseCode();

                    if (response_code == HttpURLConnection.HTTP_OK) {

                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        return (result.toString());
                    } else {
                        return ("unsuccessful");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                } finally {
                    conn.disconnect();
                }
            } else {
                return "Немате интернет конекција";
            }

        }

        @Override
        protected void onPostExecute(final String result) {

            if (isNetworkAvailable()) {

                try {
                    JSONArray jArray = new JSONArray(result);

                    HomeContent.ITEMS.clear();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                        HomeContent.ITEMS.add(new News(json_data.getString("id"), json_data.getString("title"), json_data.getString("content"), json_data.getString("type"), json_data.getString("image_url")));

                    }
                    Log.i("rann", "done");

                } catch (JSONException e) {
                    Log.i("GRESKA", e.toString());
                    Log.i("GRESKA", e.getMessage());
                    Toast.makeText(SplashScreen.this, e.toString(), Toast.LENGTH_LONG).show();
                } finally {




                    Log.i("fajnli", "yes");
                    Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Log.i("nonet", "sorry");
                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    private class AsyncFetchSchedule extends AsyncTask<String, String, String> {
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        //ProgressDialog pdLoading;
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pdLoading = ProgressDialog.show(SplashScreen.this, "", "", true);
        }

        @Override
        protected String doInBackground(String... params) {

            if (isNetworkAvailable()) {
                try {
                    // РАСПОРЕД
                    url = new URL("http://pastebin.com/raw/0ZV4P9ZV");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return e.toString();
                }
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("GET");

                    conn.setDoOutput(true);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return e1.toString();
                }

                try {

                    int response_code = conn.getResponseCode();

                    if (response_code == HttpURLConnection.HTTP_OK) {

                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        return (result.toString());
                    } else {
                        return ("unsuccessful");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                } finally {
                    conn.disconnect();
                }
            } else {
                return "Немате интернет конекција";
            }

        }

        @Override
        protected void onPostExecute(final String result) {

            if (isNetworkAvailable()) {

                try {
                    JSONArray jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                        db.addLecture(new Lecture(json_data.getString("id"), json_data.getString("subject_id"), json_data.getString("subject_type"), json_data.getString("staff_id"), json_data.getString("classroom_id"), json_data.getString("from"), json_data.getString("to"), json_data.getInt("day")));

                    }
                    Log.i("rann", "done");
                    editor = lectures.edit();
                    editor.putInt("version", version);
                    editor.commit();

                } catch (JSONException e) {
                    Log.i("GRESKA", e.toString());
                    Log.i("GRESKA", e.getMessage());
                    Toast.makeText(SplashScreen.this, e.toString(), Toast.LENGTH_LONG).show();
                } finally {

//                    new AsyncFetchScheduleVersion().execute();
//
//                    Log.i("fajnli", "yes");
//                    Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            } else {
//                Log.i("nonet", "sorry");
//                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
//                startActivity(intent);
//                finish();
            }

        }
    }
}
