package com.gc.materialdesigndemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.LayoutRipple;
import com.gc.materialdesign.widgets.ColorSelector;
import com.gc.materialdesign.widgets.ColorSelector.OnColorSelectedListener;
import com.gc.materialdesigndemo.R;
import com.nineoldandroids.view.ViewHelper;


public class MainActivity extends Activity implements OnColorSelectedListener{
	
	int backgroundColor = Color.parseColor("#1E88E5");
	ButtonFloatSmall buttonSelectColor;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        buttonSelectColor = (ButtonFloatSmall) findViewById(R.id.buttonColorSelector);
        buttonSelectColor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ColorSelector colorSelector = new ColorSelector(MainActivity.this, backgroundColor, MainActivity.this);
				colorSelector.show();
			}
		});
        
        LayoutRipple layoutRipple = (LayoutRipple) findViewById(R.id.itemButtons);
        
        
        setOriginRiple(layoutRipple);
        
        layoutRipple.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,ButtonsActivity.class);
				intent.putExtra("BACKGROUND", backgroundColor);
				startActivity(intent);
			}
		});
        layoutRipple = (LayoutRipple) findViewById(R.id.itemSwitches);
        
        
        setOriginRiple(layoutRipple);
        
        layoutRipple.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View arg0) {
        		Intent intent = new Intent(MainActivity.this,SwitchActivity.class);
        		intent.putExtra("BACKGROUND", backgroundColor);
        		startActivity(intent);
        	}
        });
        layoutRipple = (LayoutRipple) findViewById(R.id.itemProgress);
        
        
        setOriginRiple(layoutRipple);
        
        layoutRipple.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View arg0) {
        		Intent intent = new Intent(MainActivity.this,ProgressActivity.class);
        		intent.putExtra("BACKGROUND", backgroundColor);
        		startActivity(intent);
        	}
        });
        layoutRipple = (LayoutRipple) findViewById(R.id.itemWidgets);
        
        
        setOriginRiple(layoutRipple);
        
        layoutRipple.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View arg0) {
        		Intent intent = new Intent(MainActivity.this,WidgetActivity.class);
        		intent.putExtra("BACKGROUND", backgroundColor);
        		startActivity(intent);
        	}
        });
    }
    
	private void setOriginRiple(final LayoutRipple layoutRipple){
    	
    	layoutRipple.post(new Runnable() {
			
			@Override
			public void run() {
				View v = layoutRipple.getChildAt(0);
		    	layoutRipple.setxRippleOrigin(ViewHelper.getX(v) + v.getWidth() / 2);
		    	layoutRipple.setyRippleOrigin(ViewHelper.getY(v) + v.getHeight() / 2);
		    	layoutRipple.setRippleColor(Color.parseColor("#1E88E5"));
		    	
		    	layoutRipple.setRippleSpeed(50);
			}
		});
    	
    }

	@Override
	public void onColorSelected(int color) {
		backgroundColor = color;
		buttonSelectColor.setBackgroundColor(color);
	}  
    

}
