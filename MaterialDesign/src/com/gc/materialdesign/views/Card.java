package com.gc.materialdesign.views;

import com.gc.materialdesign.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class Card extends CustomView {
	
	private TextView textButton;
	
	private int paddingTop,paddingBottom, paddingLeft, paddingRight;
	
	public Card(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
	}
	
	@Override
	protected void onInitDefaultValues() {
		// TODO 自动生成的方法存根
		minWidth = 20;
		minHeight = 20;
		backgroundColor = Color.parseColor("#FFFFFF");
		backgroundResId = R.drawable.background_button_rectangle;
	}
	
	// Set color of background
	public void setBackgroundColor(int color){
		this.backgroundColor = color;
		if(isEnabled()) {
			beforeBackground = backgroundColor;
		}
		GradientDrawable shape = null;
		if (!isInEditMode()) {
			LayerDrawable layer = (LayerDrawable) getBackground();
			shape =  (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
			shape.setColor(backgroundColor);
		}
		
	}
	
}
