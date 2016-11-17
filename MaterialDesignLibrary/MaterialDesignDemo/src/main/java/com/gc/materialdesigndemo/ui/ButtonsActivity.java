package com.gc.materialdesigndemo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesigndemo.R;


public class ButtonsActivity extends Activity {

    int backgroundColor = Color.parseColor("#1E88E5");

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);
        findViewById(R.id.buttonflat).setBackgroundColor(color);
        findViewById(R.id.button).setBackgroundColor(color);
        findViewById(R.id.buttonFloatSmall).setBackgroundColor(color);
        findViewById(R.id.buttonIcon).setBackgroundColor(color);
        findViewById(R.id.buttonFloat).setBackgroundColor(color);
        findViewById(R.id.buttonFloat).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ButtonsActivity.this, "onlongclick", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        ((ButtonFlat) findViewById(R.id.buttonflat)).getTextView().setTextSize(12f);
        ((ButtonFlat) findViewById(R.id.buttonflat)).setTextSingleLine(true);
    }


}
