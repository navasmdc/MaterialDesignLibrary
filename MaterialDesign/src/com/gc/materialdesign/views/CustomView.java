package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.gc.materialdesign.utils.Utils;

public class CustomView extends RelativeLayout{
	
	final static String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
	final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";
	
	int minWidth;
	int minHeight;
	
	int backgroundColor;
	int beforeBackground;
	
	int rippleSize = 3;//手指按在控件上，产生的圆形涟漪的大小
	Integer rippleColor = null;//the color of ripple
	float rippleSpeed = 10f;//the speed of ripple translate
	
	// Indicate if user touched this view the last time
	public boolean isLastTouch = false;
	protected boolean clickAfterRipple = true;//view is click until the ripple is end
	
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * set the min size of the view
	 */
	protected void setViewSize() {
		setMinimumHeight(Utils.dpToPx(minHeight, getResources()));
		setMinimumWidth(Utils.dpToPx(minWidth, getResources()));
	}
	
	/**
	 * 设置背景色
	 * Set background Color
	 */
	protected void setBackgroundAttributes(AttributeSet attrs) {
		int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML,"background",-1);
		if(bacgroundColor != -1){
			setBackgroundColor(getResources().getColor(bacgroundColor));
		}else{
			// Color by hexadecimal
			int background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
			if(background != -1 && !isInEditMode()) {
				setBackgroundColor(background);
			}
		}
		
	}
	
	/**
	 * Make a dark color to press effect
	 * @return
	 */
	protected int makePressColor(int alpha) {
		int r = (this.backgroundColor >> 16) & 0xFF;
		int g = (this.backgroundColor >> 8) & 0xFF;
		int b = (this.backgroundColor >> 0) & 0xFF;
		r = (r - 30 < 0) ? 0 : r - 30;
		g = (g - 30 < 0) ? 0 : g - 30;
		b = (b - 30 < 0) ? 0 : b - 30;
		return Color.argb(alpha, r, g, b);
	}

}
