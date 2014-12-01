package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public abstract class Button extends RippleView {

	protected int backgroundResId;// view形状的资源

	protected OnClickListener onClickListener;

	public Button(Context context, AttributeSet attrs) {
		super(context, attrs);
		onInitDefaultValues();
		onInitAttributes(attrs);
	}
	
	protected void onInitDefaultValues() {
		backgroundColor = Color.parseColor("#1E88E5");// 默认的背景色，蓝色
		//backgroundColor = Color.parseColor("#FF88E5");// 默认的背景色，蓝色
		if (!isInEditMode()) {
			/**
			 * 默认的资源，这里因为没有初始化，所以需要在子类中初始化这个资源id。
			 * 所以子类必须在设置好资源后，调用super语句
			 */
			setBackgroundResource(backgroundResId);
		}
		// 如果是扁平按钮就不用设置背景了，因为扁平按钮背景是透明的，如果是传统或者是圆形按钮就需要设置
		setBackgroundColor(backgroundColor);
	}

	// Set atributtes of XML to View
	protected void onInitAttributes(AttributeSet attrs) {
		setViewSize();
		setBackgroundAttributes(attrs);
		setRippleAttributes(attrs);
		beforeBackground = backgroundColor;
		if(rippleColor == null) {
			rippleColor = makePressColor(255);
		}
	}

	// ### RIPPLE EFFECT ###
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

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
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
		Bitmap output = Bitmap.createBitmap(
				getWidth() - Utils.dpToPx(6, getResources()), 
				getHeight() - Utils.dpToPx(7, getResources()), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.drawARGB(0, 0, 0, 0);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(rippleColor);
		canvas.drawCircle(x, y, radius, paint);
		if (radius > getHeight() / rippleSize) {
			radius += rippleSpeed;
		}
		if (radius >= getWidth()) {
			x = -1;
			y = -1;
			radius = getHeight() / rippleSize;
			// 如果在这里设置监听器的话，就是动画结束后才执行点击事件 
			if (clickAfterRipple == true && onClickListener != null) {
				onClickListener.onClick(this);
			}
		}
		return output;
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		onClickListener = l;
	}

	// Set color of background
	public void setBackgroundColor(int color) {
		backgroundColor = color;
		if (isEnabled())
			beforeBackground = backgroundColor;
		try {
			LayerDrawable layer = (LayerDrawable) getBackground();
			// 每个按钮的框架都是由drawable中的xml文件制定的，xml文件中都有一个item的id叫：shape_bacground
			GradientDrawable shape = (GradientDrawable) layer
					.findDrawableByLayerId(R.id.shape_bacground);
			/**
			 * 给这个图片设置背景色，因为图片的主体是透明的所以可以直接显示背景色
			 * 效果就是一个透明但有阴影的框架下有了背景色，这样的方式可以方便的设置不同颜色的按钮，让按钮看起来还是浑然一体
			 */
			shape.setColor(backgroundColor);
			rippleColor = super.makePressColor(255);
		} catch (Exception ex) {
			// Without bacground
		}
	}

	abstract public TextView getTextView();
	
	/**
	 * @return 涟漪上的暗色
	 * 如果自定义了颜色，就返回自定义的颜色。如果没有，那么就生成颜色
	 */
	@Override
	protected int makePressColor(int alpha){
		if (rippleColor != null) {
			return rippleColor;	
		}else {
			return super.makePressColor(alpha);
		}
	}
}
