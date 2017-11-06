package com.example.user.pretendingnews.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.pretendingnews.R;
import com.example.user.pretendingnews.holders.TitleNewsHolder;
import com.example.user.pretendingnews.holders.NormalNewsHollder;
import com.example.user.pretendingnews.models.NewsItem;
import com.example.user.pretendingnews.models.NormalItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 2017-11-05.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > implements View.OnTouchListener{

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
            Picasso.with(context).load(tmp).into(newsHolder.titleImage);
            newsHolder.titleImage.setOnTouchListener(this);
        } else if (holder.getItemViewType() == NORMAL_TYPE) {
            final NormalNewsHollder normalNewsHolder = (NormalNewsHollder) holder;
            Picasso.with(context).load(((NormalItem) items.get(position)).getFirstImage()).into(normalNewsHolder.firstNewsImage);
            Picasso.with(context).load(((NormalItem) items.get(position)).getSecondImage()).into(normalNewsHolder.secondNewsImage);
            Log.d("--imageLog Check", ((NormalItem) items.get(position)).getSecondImage());
            normalNewsHolder.secondNewsImage.setOnTouchListener(this);
            normalNewsHolder.firstNewsImage.setOnTouchListener(this);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                ImageView view = (ImageView) v;
                //overlay is black with transparency of 0x77 (119)
                view.getDrawable().setColorFilter(0x77000000,PorterDuff.Mode.SRC_ATOP);
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
                v.performClick();
            case MotionEvent.ACTION_CANCEL: {
                ImageView view = (ImageView) v;
                //clear the overlay
                view.getDrawable().clearColorFilter();
                view.invalidate();
                break;
            }
        }

        return true;
    }
}
