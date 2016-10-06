package com.example.hristijan.tabs2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hristijan.tabs2.dummy.HomeContent;
import com.example.hristijan.tabs2.items.News;

public class NewsContentActivity extends AppCompatActivity {

    TextView title, content;
    String newsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        newsID = getIntent().getStringExtra("newsID");
        title = (TextView)findViewById(R.id.news_title_a);
        content = (TextView)findViewById(R.id.news_content_a);

        Log.i("NEWZid", newsID);

        for(Object o : HomeContent.ITEMS){
            if(o instanceof News) {
                News n = (News)o;
                Log.i("NEWSITEM", n.toString());
                if (n.getID().equals(newsID)) {
                    setTitle(n.getDate());
                    title.setText(n.getTitle());
                    content.setText(n.getContent());
                    break;
                }
            }
        }



    }
}
