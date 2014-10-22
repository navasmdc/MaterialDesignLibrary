package com.gc.materialdesign.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.gc.materialdesign.R;
import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.views.Slider.OnValueChangedListener;

public class ColorSelector extends android.app.Dialog implements OnValueChangedListener{
	
	int color = Color.BLACK;
	View colorView;
	
	OnColorSelectedListener onColorSelectedListener;
	Slider red, green, blue;
	

	public ColorSelector(Context context,Integer color, OnColorSelectedListener onColorSelectedListener) {
		super(context, android.R.style.Theme_Translucent);
		
		this.onColorSelectedListener = onColorSelectedListener;
		if(color != null)
			this.color = color;
		setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(ColorSelector.this.onColorSelectedListener != null)
					ColorSelector.this.onColorSelectedListener.onColorSelected(ColorSelector.this.color);
			}
		});
		
	}
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.color_selector);
	    
	    colorView = findViewById(R.id.viewColor);
	    colorView.setBackgroundColor(color);
	    // Resize ColorView
	    colorView.post(new Runnable() {
			
			@Override
			public void run() {
				LinearLayout.LayoutParams params = (LayoutParams) colorView.getLayoutParams();
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
	public void onValueChanged(int value) {
		color = Color.rgb(red.getValue(), green.getValue(), blue.getValue());
		colorView.setBackgroundColor(color);
	}
	
	
	// Event that execute when color selector is closed
		public interface OnColorSelectedListener{
			public void onColorSelected(int color);
		}
	

}
