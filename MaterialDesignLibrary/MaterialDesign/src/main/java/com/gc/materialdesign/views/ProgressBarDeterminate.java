package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.AttributesUtils;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ProgressBarDeterminate extends CustomView {


    int max = 100;
    int min = 0;
    int progress = 0;

    int backgroundColor = Color.parseColor("#1E88E5");

    View progressView;

    public ProgressBarDeterminate(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs.getStyleAttribute(), AttributesUtils.attrs);
        setAttributes(attrs, typedArray);
    }

    // Set atributtes of XML to View
    protected void setAttributes(AttributeSet attrs, TypedArray style){

        progressView = new View(getContext());
        LayoutParams params = new LayoutParams(1, 1);
        progressView.setLayoutParams(params);
        progressView.setBackgroundResource(R.drawable.background_progress);
        addView(progressView);

        //Set background Color
        // Color by resource
        int bacgroundColor = AttributesUtils.getBackgroundColor(getResources(),attrs,style);
        if (bacgroundColor != -1)
            setBackgroundColor(bacgroundColor);

        min = AttributesUtils.getMin(getResources(),attrs,style,0);
        max = AttributesUtils.getMax(getResources(),attrs,style,100);
        progress = AttributesUtils.getProgress(getResources(),attrs,style,min);

        setMinimumHeight(Utils.dpToPx(3, getResources()));

        post(new Runnable() {

            @Override
            public void run() {
                LayoutParams params = (LayoutParams) progressView.getLayoutParams();
                params.height = getHeight();
                progressView.setLayoutParams(params);
            }
        });

    }

    /**
     * Make a dark color to ripple effect
     *
     * @return
     */
    protected int makePressColor() {
        int r = (this.backgroundColor >> 16) & 0xFF;
        int g = (this.backgroundColor >> 8) & 0xFF;
        int b = (this.backgroundColor >> 0) & 0xFF;
        return Color.argb(128, r, g, b);
    }

    // SETTERS

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pendindProgress != -1)
            setProgress(pendindProgress);
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    int pendindProgress = -1;

    public void setProgress(int progress) {
        if (getWidth() == 0) {
            pendindProgress = progress;
        } else {
            this.progress = progress;
            if (progress > max)
                progress = max;
            if (progress < min)
                progress = min;
            int totalWidth = max - min;
            double progressPercent = (double) progress / (double) totalWidth;
            int progressWidth = (int) (getWidth() * progressPercent);
            LayoutParams params = (LayoutParams) progressView.getLayoutParams();
            params.width = progressWidth;
            params.height = getHeight();
            progressView.setLayoutParams(params);
            pendindProgress = -1;
        }
    }

    public int getProgress() {
        return progress;
    }

    // Set color of background
    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        if (isEnabled())
            beforeBackground = backgroundColor;
        LayerDrawable layer = (LayerDrawable) progressView.getBackground();
        GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_background);
        shape.setColor(color);
        super.setBackgroundColor(makePressColor());
    }

}
