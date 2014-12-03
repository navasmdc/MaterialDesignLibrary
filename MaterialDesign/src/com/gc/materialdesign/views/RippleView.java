package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

public abstract class RippleView extends CustomView{
	
	protected boolean settedRippleColor = false;
	
	protected int rippleSize = 3;//手指按在控件上，产生的圆形涟漪的大小
	protected Integer rippleColor = null;//the color of ripple
	protected float rippleSpeed;//the speed of ripple translate
	
	protected OnClickListener onClickListener;
	protected boolean clickAfterRipple = true;//view is click until the ripple is end
	
	public RippleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
	}
	
	// Set atributtes of XML to View
	@Override
	protected void setAttributes(AttributeSet attrs) {
		super.setAttributes(attrs);
		setRippleAttributes(attrs);
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
			settedRippleColor = true;
		}else{
			// Color by hexadecimal
			int rColor = attrs.getAttributeIntValue(MATERIALDESIGNXML, "rippleColor", -1);// 16进制的颜色
			if(rColor != -1 && !isInEditMode()) {
				rippleColor = rColor;
				settedRippleColor = true;
			}
		}
		
		/**
		 * 初始化涟漪扩展的速度 
		 * init Ripple speed
		 */
		rippleSpeed = attrs.getAttributeFloatValue(MATERIALDESIGNXML, "rippleSpeed", rippleSpeed);
		
		/**
		 * 设定涟漪的相应时间
		 */
		clickAfterRipple = attrs.getAttributeBooleanValue(MATERIALDESIGNXML, "clickAfterRipple", clickAfterRipple);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// super.onInterceptTouchEvent(ev);
		return true;
	}
	
	// ripple position
	protected float x = -1;
	protected float y = -1;
	protected float radius = -1;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isEnabled()) {
			isLastTouch = true;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				radius = getHeight() / rippleSize;
				x = event.getX();
				y = event.getY();
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				radius = getHeight() / rippleSize;
				x = event.getX();
				y = event.getY();
				if (!((event.getX() <= getWidth() && event.getX() >= 0) && 
						(event.getY() <= getHeight() && event.getY() >= 0))) {
					isLastTouch = false;
					x = -1;
					y = -1;
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if ((event.getX() <= getWidth() && event.getX() >= 0)
						&& (event.getY() <= getHeight() && event.getY() >= 0)) {
					radius++;
				} else {
					isLastTouch = false;
					x = -1;
					y = -1;
				}
				if (clickAfterRipple == false && onClickListener != null) {
					onClickListener.onClick(this);
				}
			}
		}
		return true;
	}
	
	public Bitmap makeCircleFromBitmap(Bitmap output) {
		Canvas canvas = new Canvas(output);
		canvas.drawARGB(0, 0, 0, 0);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		if (rippleColor == null) {
			paint.setColor(makePressColor(255));
		}else {
			paint.setColor(rippleColor);
		}
		canvas.drawCircle(x, y, radius, paint);
		if (radius > getHeight() / rippleSize)
			radius += rippleSpeed;
		if (radius >= getWidth()) {
			x = -1;
			y = -1;
			radius = getHeight() / rippleSize;
			if(clickAfterRipple == true && onClickListener != null)
				onClickListener.onClick(this);
		}
		return output;
	}
	
	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect) {
		if (!gainFocus) {
			x = -1;
			y = -1;
		}
	}
	
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		onClickListener = l;
	}
	
	/**
	 * set and get ripple color
	 * @param color
	 */
	public void setRippleColor(int color) {
		rippleColor = color;
		settedRippleColor = true;
	}
	
	public int getRippleColor() {
		if (rippleColor != null) {
			return rippleColor;
		}else {
			return makePressColor(255);
		}
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
