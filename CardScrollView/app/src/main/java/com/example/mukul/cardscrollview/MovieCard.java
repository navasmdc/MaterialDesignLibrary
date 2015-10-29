package com.example.mukul.cardscrollview;

/**
 * Created by mukul on 10/21/2015.
 */

import com.google.android.glass.app.Card.ImageLayout;

public class MovieCard {

    private String text;
    private String footerText;
    private ImageLayout imgLayout;
    private int[] images;

    public MovieCard() {
    }

    public MovieCard(String text, String footerText,
                     ImageLayout imgLayout, int[] images) {
        this.text = text;
        this.footerText = footerText;
        this.imgLayout = imgLayout;
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public ImageLayout getImgLayout() {
        return imgLayout;
    }

    public void setImgLayout(ImageLayout imgLayout) {
        this.imgLayout = imgLayout;
    }

    public int[] getImages() {
        return images;
    }

    public void setImages(int[] images) {
        this.images = images;
    }

}