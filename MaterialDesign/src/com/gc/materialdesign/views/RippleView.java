package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;

public abstract class RippleView extends CustomView{

	public RippleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
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
		 * 初始化涟漪扩展的速度 
		 * init Ripple speed
		 */
		rippleSpeed = attrs.getAttributeFloatValue(MATERIALDESIGNXML, "rippleSpeed", rippleSpeed);
		clickAfterRipple = attrs.getAttributeBooleanValue(MATERIALDESIGNXML, "clickAfterRipple", clickAfterRipple);
	}
	
	/**
	 * set and get ripple color
	 * @param color
	 */
	public void setRippleColor(int color) {
		rippleColor = color;
	}
	
	public int getRippleColor() {
		return rippleColor;
	}
	
	/**
	 * set and get ripple speed
	 * @param speed
	 */
	public void setRippleSpeed(float speed) {
		rippleSpeed = speed;
	}
	
	public float getRippleSpeed() {
		return rippleSpeed;
	}
	
	/**
	 * 设置什么时候响应点击事件，是手指按上去就相应，还是等涟漪扩散完再响应
	 * @param clickAfterRipple
	 */
	public void setClickAfterRipple(boolean clickAfterRipple) {
		this.clickAfterRipple = clickAfterRipple;
	}
	
	public boolean getClickAfterRipple() {
		return clickAfterRipple;
	}

}
