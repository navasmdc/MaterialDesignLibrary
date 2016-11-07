package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ButtonFlat extends Button {
	
	TextView textButton;

	int paddingTop, paddingBottom, paddingLeft, paddingRight;

	public ButtonFlat(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	protected void setDefaultProperties(){
		minHeight = 36;
		minWidth = 88;
		rippleSize = 3;
		paddingBottom = Utils.dpToPx(16, getResources());
		paddingLeft = Utils.dpToPx(16, getResources());
		paddingRight = Utils.dpToPx(16, getResources());
		paddingTop = Utils.dpToPx(16, getResources());
		// Min size
		setMinimumHeight(Utils.dpToPx(minHeight, getResources()));
		setMinimumWidth(Utils.dpToPx(minWidth, getResources()));
		setBackgroundResource(R.drawable.background_transparent);
	}

	@Override
	protected void setAttributes(AttributeSet attrs, TypedArray typedArray) {

		// Set Padding
		String value = attrs.getAttributeValue(ANDROIDXML, "padding");
		if (value != null) {
			float padding = Float.parseFloat(value.replace("dip", ""));
			paddingBottom = Utils.dpToPx(padding, getResources());
			paddingLeft = Utils.dpToPx(padding, getResources());
			paddingRight = Utils.dpToPx(padding, getResources());
			paddingTop = Utils.dpToPx(padding, getResources());
		} else {
			value = attrs.getAttributeValue(ANDROIDXML, "paddingLeft");
			paddingLeft = (value == null) ? paddingLeft : (int) Float.parseFloat(value.replace("dip", ""));
			value = attrs.getAttributeValue(ANDROIDXML, "paddingTop");
			paddingTop = (value == null) ? paddingTop : (int) Float.parseFloat(value.replace("dip", ""));
			value = attrs.getAttributeValue(ANDROIDXML, "paddingRight");
			paddingRight = (value == null) ? paddingRight : (int) Float.parseFloat(value.replace("dip", ""));
			value = attrs.getAttributeValue(ANDROIDXML, "paddingBottom");
			paddingBottom = (value == null) ? paddingBottom : (int) Float.parseFloat(value.replace("dip", ""));
		}
		setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

		// Set text button
		String text = null;
		int textResource = attrs.getAttributeResourceValue(ANDROIDXML,"text",-1);
		if(textResource != -1){
			text = getResources().getString(textResource);
		}else{
			text = attrs.getAttributeValue(ANDROIDXML,"text");
		}
		textButton = new TextView(getContext());
		if(text != null){
			textButton.setText(text.toUpperCase());
			textButton.setTextColor(backgroundColor);
			textButton.setTypeface(null, Typeface.BOLD);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			textButton.setLayoutParams(params);
			addView(textButton);
		}
		int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML,"background",-1);
		if(bacgroundColor != -1){
			setBackgroundColor(getResources().getColor(bacgroundColor));
		}else{
			// Color by hexadecimal
			// Color by hexadecimal
			background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
			if (background != -1)
				setBackgroundColor(background);
		}
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (x != -1) {
			
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(makePressColor());
			canvas.drawCircle(x, y, radius, paint);
			if(radius > getHeight()/rippleSize)
				radius += rippleSpeed;
			if(radius >= getWidth()){
				x = -1;
				y = -1;
				radius = getHeight()/rippleSize;
				if(onClickListener != null&& clickAfterRipple)
					onClickListener.onClick(this);
			}
			invalidate();
		}		
		
	}
	
	/**
	 * Make a dark color to ripple effect
	 * @return
	 */
	@Override
	protected int makePressColor(){
		return Color.parseColor("#88DDDDDD");	
	}
	
	public void setText(String text){
		textButton.setText(text.toUpperCase());
	}
	
	// Set color of background
	public void setBackgroundColor(int color){
		backgroundColor = color;
		if(isEnabled())
			beforeBackground = backgroundColor;
		textButton.setTextColor(color);
	}

	@Override
	public TextView getTextView() {
		return textButton;
	}
	
	public String getText(){
        	return textButton.getText().toString();
 	}

}
