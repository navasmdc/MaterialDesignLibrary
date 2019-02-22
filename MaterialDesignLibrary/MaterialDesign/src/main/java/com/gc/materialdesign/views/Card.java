package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.AttributesUtils;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class Card extends CustomView {
	
	TextView textButton;
	
	int paddingTop,paddingBottom, paddingLeft, paddingRight;
	int backgroundColor = Color.parseColor("#FFFFFF");
	
	public Card(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs.getStyleAttribute(), AttributesUtils.attrs);
		setAttributes(attrs, typedArray);
	}
	
	
	// Set atributtes of XML to View
	protected void setAttributes(AttributeSet attrs, TypedArray style){


		
		setBackgroundResource(R.drawable.background_button_rectangle);
		//Set background Color
		// Color by resource
		int bacgroundColor = AttributesUtils.getBackgroundColor(getResources(),attrs,style);
		if(bacgroundColor != -1)
			setBackgroundColor(getResources().getColor(bacgroundColor));
	}
	
	// Set color of background
	public void setBackgroundColor(int color){
		this.backgroundColor = color;
		if(isEnabled())
			beforeBackground = backgroundColor;
		LayerDrawable layer = (LayerDrawable) getBackground();
		GradientDrawable shape =  (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_background);
		shape.setColor(backgroundColor);
	}
	
}
