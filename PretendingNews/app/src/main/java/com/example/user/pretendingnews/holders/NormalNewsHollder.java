package com.example.user.pretendingnews.holders;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.user.pretendingnews.R;

/**
 * Created by user on 2017-11-05.
 */

public class NormalNewsHollder extends RecyclerView.ViewHolder {

    public ImageView firstNewsImage;
    public ImageView secondNewsImage;

    public NormalNewsHollder(View itemView) {
        super(itemView);

        firstNewsImage = (ImageView) itemView.findViewById(R.id.firstImage);
        secondNewsImage = (ImageView) itemView.findViewById(R.id.secondImage);
    }
}
