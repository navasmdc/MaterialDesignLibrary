package com.gc.materialdesigndemo.ui;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.ColorSelector;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.gc.materialdesigndemo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class WidgetActivity extends Activity {

    private int backgroundColor = Color.parseColor("#1E88E5");
    private SnackBar snackBar;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgets);

//		SHOW SNACKBAR
        findViewById(R.id.buttonSnackBar).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View flatButton) {
                snackBar = new SnackBar(WidgetActivity.this,
                        "Do you want change color of this button to red?",
                        "yes", new OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        ButtonFlat btn = (ButtonFlat) findViewById(R.id.buttonSnackBar);
                        btn.setTextColor(Color.RED);
                    }
                });
                snackBar.show();
            }
        });

//		SHOW DiALOG
        findViewById(R.id.buttonDialog).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View flatButton) {
                Dialog dialog = new Dialog(WidgetActivity.this, "Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam");
                dialog.setOnAcceptButtonClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(WidgetActivity.this, "Click accept button", 1).show();
                        if (snackBar != null) {
                            snackBar.dismiss();
                            new SnackBar(WidgetActivity.this, "thread problem", "accept", new OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                        }
                    }
                });
                dialog.setOnCancelButtonClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(WidgetActivity.this, "Click cancel button", 1).show();
                    }
                });
                dialog.show();
            }
        });

        findViewById(R.id.buttonProgressDialog).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View flatButton) {
                ProgressDialog progressDialog = new ProgressDialog(WidgetActivity.this, "Loading");
                progressDialog.show();
            }
        });

//		SHOW COLOR SEECTOR
        findViewById(R.id.buttonColorSelector).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View flatButton) {
                new ColorSelector(WidgetActivity.this, Color.RED, null).show();
            }
        });
        findViewById(R.id.buttonDialog).performClick();
//		ViewPager vp_slider = (ViewPager) findViewById(R.id.vp_slider);
//		vp_slider.setAdapter(pagerAdapter);
    }


    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(WidgetActivity.this);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_progress, container, false);
            container.addView(layout);
            return layout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };
}
