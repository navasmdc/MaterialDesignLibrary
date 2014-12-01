package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class LayoutRipple extends RippleView {

	OnClickListener onClickListener;

	Float xRippleOrigin;
	Float yRippleOrigin;
	float rippleBorderRadius = 0;// radius
	
	public LayoutRipple(Context context, AttributeSet attrs) {
		super(context, attrs);
		minWidth = 20;
		minHeight = 20;
		backgroundColor = 0x00ffffff;
		rippleSpeed = 20f;// default speed. layout is large,so need fast
		setAttributes(attrs);
	}
	
	// Set atributtes of XML to View
	protected void setAttributes(AttributeSet attrs){
		setBackgroundColor(backgroundColor);
		setBackgroundAttributes(attrs);
		setRippleAttributes(attrs);
		//设定涟漪最外层的边界弧度，是圆角矩形，如果不设置，则是普通矩形
		// layout border radius
		rippleBorderRadius = attrs.getAttributeFloatValue(MATERIALDESIGNXML, "rippleBorderRadius", 0);
	}
	
	// ### RIPPLE EFFECT ###

	float x = -1, y = -1;//x，y坐标
	float radius = -1;//半径
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isEnabled()) {
			isLastTouch = true;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				radius = getHeight() / rippleSize; // 控件高度/涟漪大小 = 半径
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
				if(clickAfterRipple == false && onClickListener != null)
					onClickListener.onClick(this);
			}
		}
		return true;
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
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// super.onInterceptTouchEvent(ev);
		return true;
	}
	
	public Bitmap makeCircle() {
		Bitmap output = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.drawARGB(0, 0, 0, 0);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		if (rippleColor == null) {
			rippleColor = makePressColor(255);
		}
		paint.setColor(rippleColor);
		x = (xRippleOrigin == null) ? x : xRippleOrigin;
		y = (yRippleOrigin == null) ? y : yRippleOrigin;
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
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (x != -1) {
			Rect src = new Rect(0, 0, getWidth(), getHeight());
			Rect dst = new Rect(0, 0, getWidth(), getHeight());
			canvas.drawBitmap(cropRoundRect(makeCircle()), src, dst, null);
		}
		invalidate();
	}
	
	/**
	 * @param bitmap
	 * @return 设置涟漪的边界，涟漪在这个区域里面可见。这里可以设置四角的弧度数
	 */
	public Bitmap cropRoundRect(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	    bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    canvas.drawRoundRect(rectF, rippleBorderRadius, rippleBorderRadius, paint);

	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	    return output;
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		onClickListener = l;
	}

	public void setxRippleOrigin(Float xRippleOrigin) {
		this.xRippleOrigin = xRippleOrigin;
	}


	public void setyRippleOrigin(Float yRippleOrigin) {
		this.yRippleOrigin = yRippleOrigin;
	}
	
	/**
	 * set the layoutRipple border radius
	 * @param size
	 */
	public void setRippleBorderRadius(float size) {
		rippleBorderRadius = size;
	}
	
	// Set color of background
	public void setBackgroundColor(int color){
		backgroundColor = color;
		super.setBackgroundColor(color);
	}
	
	
}
