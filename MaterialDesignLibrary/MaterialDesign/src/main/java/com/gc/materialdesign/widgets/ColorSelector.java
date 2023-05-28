package com.gc.materialdesign.widgets;

import com.gc.materialdesign.R;
import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.views.Slider.OnValueChangedListener;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class ColorSelector extends android.app.Dialog implements OnValueChangedListener {

    int color = Color.BLACK;

    Context context;

    View colorView;

    //background
    View view, backView;

    OnColorSelectedListener onColorSelectedListener;

    Slider red, green, blue;

    public ColorSelector(Context context, Integer color, OnColorSelectedListener onColorSelectedListener) {
        super(context, android.R.style.Theme_Translucent);
        this.context = context;
        this.onColorSelectedListener = onColorSelectedListener;
        if (color != null)
            this.color = color;
        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (ColorSelector.this.onColorSelectedListener != null)
                    ColorSelector.this.onColorSelectedListener.onColorSelected(ColorSelector.this.color);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_selector);
        view = (LinearLayout) findViewById(R.id.contentSelector);
        backView = (RelativeLayout) findViewById(R.id.rootSelector);
        backView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() < view.getLeft() || event.getX() > view.getRight() || event.getY() > view.getBottom() || event.getY() < view.getTop()) {
                    dismiss();
                }
                return false;
            }
        });
        colorView = findViewById(R.id.viewColor);
        colorView.setBackgroundColor(color);
        // Resize ColorView
        colorView.post(new Runnable() {

            @Override
            public void run() {
                LayoutParams params = (LayoutParams) colorView.getLayoutParams();
                params.height = colorView.getWidth();
                colorView.setLayoutParams(params);
            }
        });
        // Configure Sliders
        red = (Slider) findViewById(R.id.red);
        green = (Slider) findViewById(R.id.green);
        blue = (Slider) findViewById(R.id.blue);
        int r = (this.color >> 16) & 0xFF;
        int g = (this.color >> 8) & 0xFF;
        int b = (this.color >> 0) & 0xFF;
        red.setValue(r);
        green.setValue(g);
        blue.setValue(b);
        red.setOnValueChangedListener(this);
        green.setOnValueChangedListener(this);
        blue.setOnValueChangedListener(this);
    }

    @Override
    public void show() {
        super.show();
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_main_show_amination));
        backView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_root_show_amin));
    }

    @Override
    public void onValueChanged(int value) {
        color = Color.rgb(red.getValue(), green.getValue(), blue.getValue());
        colorView.setBackgroundColor(color);
    }

    // Event that execute when color selector is closed
    public interface OnColorSelectedListener {

        public void onColorSelected(int color);
    }

    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_main_hide_amination);
        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.post(new Runnable() {

                    @Override
                    public void run() {
                        ColorSelector.super.dismiss();
                    }
                });
            }
        });
        Animation backAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_root_hide_amin);
        view.startAnimation(anim);
        backView.startAnimation(backAnim);
    }
}
