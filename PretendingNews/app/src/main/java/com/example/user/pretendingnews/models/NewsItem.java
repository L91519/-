package com.example.user.pretendingnews.models;

/**
 * Created by user on 2017-11-05.
 */

public class NewsItem {
    private String mainImage;

    public NewsItem(String mainImage) {
        this.mainImage = mainImage;
    }

    public NewsItem() {
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
}
