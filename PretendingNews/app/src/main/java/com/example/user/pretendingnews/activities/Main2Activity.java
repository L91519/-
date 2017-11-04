package com.example.user.pretendingnews.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.user.pretendingnews.PretendingNewsBaseActivity;
import com.example.user.pretendingnews.R;
import com.example.user.pretendingnews.adapters.NewsAdapter;
import com.example.user.pretendingnews.models.NewsInfoItem;
import com.example.user.pretendingnews.models.NewsItem;
import com.example.user.pretendingnews.models.NormalItem;
import com.example.user.pretendingnews.network.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-11-04.
 */

public class Main2Activity extends PretendingNewsBaseActivity {

    private RecyclerView newsRecyclerView;
    private RecyclerView.LayoutManager newsManager;
    private RecyclerView.Adapter newsAdapter;
    private ArrayList<NewsInfoItem> items;

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
        getData2();
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

    public void getData2() {
        items = new ArrayList<>();
        Service.getRetrofit(getApplicationContext()).getInfo().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArray = response.body().getAsJsonArray("items");
                JsonArray jsonElements = jsonArray.getAsJsonArray();

                for(int i = 0; i < jsonElements.size(); i++) {
                    JsonObject jsonObject = (JsonObject) jsonElements.get(i);

                    String title = jsonObject.getAsJsonPrimitive("title").getAsString();
                    String originallink = jsonObject.getAsJsonPrimitive("originallink").getAsString();
                    String link = jsonObject.getAsJsonPrimitive("link").getAsString();
                    String description = jsonObject.getAsJsonPrimitive("description").getAsString();
                    String pubDate = jsonObject.getAsJsonPrimitive("pubDate").getAsString();

                    items.add(new NewsInfoItem(title, originallink, link, description, pubDate));
                }
                Log.d("arr check", items.toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
