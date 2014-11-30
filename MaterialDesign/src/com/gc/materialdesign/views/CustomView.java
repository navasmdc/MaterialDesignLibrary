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
	Integer rippleColor = null;//涟漪的颜色
	float rippleSpeed = 10f;//涟漪的传播速度
	
	// Indicate if user touched this view the last time
	public boolean isLastTouch = false;
	protected boolean clickAfterRipple = true;//view is click when the ripple is end
	
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	protected void setBackgroundAttributes(AttributeSet attrs) {
		// 最小尺寸
		setMinimumHeight(Utils.dpToPx(minHeight, getResources()));
		setMinimumWidth(Utils.dpToPx(minWidth, getResources()));
		/**
		 * 设置背景色
		 * Set background Color
		 */
		int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML,"background",-1);
		if(bacgroundColor != -1){
			setBackgroundColor(getResources().getColor(bacgroundColor));
		}else{
			// Color by hexadecimal
			int background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
			if(background != -1 && !isInEditMode()) {
				setBackgroundColor(background);
			}/*else {
				setBackgroundColor(backgroundColor);
			}*/
		}
		
	}
	
	protected void setRippleAttributes(AttributeSet attrs) {
		/**
		 * 初始化按压时涟漪的颜色
		 * Set Ripple Color
		 * Color by resource
		 */
		int color = attrs.getAttributeResourceValue(MATERIALDESIGNXML,"rippleColor",-1);
		if(color != -1){
			rippleColor = getResources().getColor(color);
		}else{
			// Color by hexadecimal
			int rColor = attrs.getAttributeIntValue(MATERIALDESIGNXML, "rippleColor", -1);// 16进制的颜色
			if(rColor != -1 && !isInEditMode()) {
				rippleColor = rColor;
			}
		}
		/**
		 * 初始化涟漪扩展的速度 init Ripple speed
		 */
		rippleSpeed = attrs.getAttributeFloatValue(MATERIALDESIGNXML, "rippleSpeed", rippleSpeed);
		clickAfterRipple = attrs.getAttributeBooleanValue(MATERIALDESIGNXML, "clickAfterRipple", clickAfterRipple);
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
	
	//设置按下时的涟漪的背景色
	public void setRippleColor(int color) {
		rippleColor = color;
	}
	
	public int getRippleColor() {
		return rippleColor;
	}
	
	/**
	 * set ripple speed
	 * @param speed
	 */
	public void setRippleSpeed(float speed) {
		rippleSpeed = speed;
	}
	
	public float getRippleSpeed() {
		return rippleSpeed;
	}

}
