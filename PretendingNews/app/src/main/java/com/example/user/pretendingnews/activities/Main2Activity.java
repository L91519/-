package com.example.user.pretendingnews.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.user.pretendingnews.PretendingNewsBaseActivity;
import com.example.user.pretendingnews.R;
import com.example.user.pretendingnews.adapters.NewsAdapter;
import com.example.user.pretendingnews.models.NewsItem;
import com.example.user.pretendingnews.models.NormalItem;

import java.util.ArrayList;

/**
 * Created by user on 2017-11-04.
 */

public class Main2Activity extends PretendingNewsBaseActivity {

    private RecyclerView newsRecyclerView;
    private RecyclerView.LayoutManager newsManager;
    private RecyclerView.Adapter newsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        newsRecyclerView.hasFixedSize();
        newsManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        newsManager.hasFocus();
        newsRecyclerView.setLayoutManager(newsManager);

        getData();
    }

    public void getData() {
        ArrayList<Object> items = new ArrayList<>();

        Object baseItem1 = (NewsItem) new NewsItem("https://i.imgur.com/99P8kd6.jpg");
        items.add(baseItem1);

        Object baseItem2 = (NormalItem) new NormalItem("https://i.imgur.com/tmlfXQr.jpg","https://cosmos-magazine.imgix.net/file/spina/photo/8178/131016_platypus_2.jpg?fit=clip&w=835");
        items.add(baseItem2);
        items.add(baseItem2);

        newsAdapter = new NewsAdapter(items, getApplicationContext());
        newsRecyclerView.setAdapter(newsAdapter);
    }
}
