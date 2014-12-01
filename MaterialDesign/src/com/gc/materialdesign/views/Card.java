package com.gc.materialdesign.views;

import com.gc.materialdesign.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class Card extends CustomView {
	
	TextView textButton;
	
	int paddingTop,paddingBottom, paddingLeft, paddingRight;
	int backgroundColor = Color.parseColor("#FFFFFF");
	
	public Card(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
	}
	
	
	// Set atributtes of XML to View
	protected void setAttributes(AttributeSet attrs){
		setBackgroundColor(this.backgroundColor);
		setBackgroundAttributes(attrs);
		if(!isInEditMode()) {
			setBackgroundResource(R.drawable.background_button_rectangle);
		}
	}
	
	// Set color of background
	public void setBackgroundColor(int color){
		this.backgroundColor = color;
		if(isEnabled()) {
			beforeBackground = backgroundColor;
		}
		LayerDrawable layer = (LayerDrawable) getBackground();
		GradientDrawable shape = null;
		if (!isInEditMode()) {
			shape =  (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
			shape.setColor(backgroundColor);
		}
		
	}
	
}
