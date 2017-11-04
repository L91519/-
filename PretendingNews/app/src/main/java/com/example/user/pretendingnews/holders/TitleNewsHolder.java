package com.example.user.pretendingnews.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.user.pretendingnews.R;

/**
 * Created by user on 2017-11-05.
 */

public class TitleNewsHolder extends RecyclerView.ViewHolder {

    public ImageView titleImage;

    public TitleNewsHolder(View itemView) {
        super(itemView);

        titleImage = (ImageView) itemView.findViewById(R.id.titleImage);
    }
}
