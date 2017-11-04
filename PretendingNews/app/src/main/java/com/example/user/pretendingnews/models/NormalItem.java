package com.example.user.pretendingnews.models;

/**
 * Created by user on 2017-11-05.
 */

public class NormalItem {

    private String firstImage;
    private String secondImage;

    public NormalItem() {
    }

    public NormalItem(String firstImage, String secondImage) {
        this.firstImage = firstImage;
        this.secondImage = secondImage;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public void setSecondImage(String secondImage) {
        this.secondImage = secondImage;
    }
}
