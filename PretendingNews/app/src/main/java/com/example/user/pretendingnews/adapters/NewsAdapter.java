package com.example.user.pretendingnews.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.user.pretendingnews.R;
import com.example.user.pretendingnews.holders.TitleNewsHolder;
import com.example.user.pretendingnews.holders.NormalNewsHollder;
import com.example.user.pretendingnews.models.NewsItem;
import com.example.user.pretendingnews.models.NormalItem;

import java.util.ArrayList;

/**
 * Created by user on 2017-11-05.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {

    private ArrayList<Object> items;
    private Context context;
    static final int TITLE_TYPE = 0;
    static final int NORMAL_TYPE = 1;

    public NewsAdapter(ArrayList<Object> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TITLE_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_news, parent, false);
            return new TitleNewsHolder(view);
        } else if(viewType == NORMAL_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal_news, parent, false);
            return new NormalNewsHollder(view);
        }
        return null;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TITLE_TYPE) {
//            holder.titleImage =(NewsItem)items.get(position).getMainImage();
            final TitleNewsHolder newsHolder = (TitleNewsHolder) holder;
            String tmp = ((NewsItem) items.get(position)).getMainImage();
            Glide.with(context).load(tmp).into(newsHolder.titleImage);
        } else if (holder.getItemViewType() == NORMAL_TYPE) {
            final NormalNewsHollder normalNewsHolder = (NormalNewsHollder) holder;
            Glide.with(context).load(((NormalItem) items.get(position)).getFirstImage()).into(normalNewsHolder.firstNewsImage);
            Glide.with(context).load(((NormalItem) items.get(position)).getSecondImage()).into(normalNewsHolder.secondNewsImage);
            Log.d("--imageLog Check", ((NormalItem) items.get(position)).getSecondImage());

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        if(position == 0) {
            viewType = 0;
        }
        return viewType;
    }
}
