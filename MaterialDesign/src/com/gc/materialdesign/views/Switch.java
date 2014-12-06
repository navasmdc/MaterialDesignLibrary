package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class Switch extends CustomView {

	private Ball ball;

	private boolean iSchecked = false;
	private boolean eventCheck = false;
	private boolean press = false;

	private OnCheckListener onCheckListener;

	public Switch(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
	}
	
	@Override
	protected void onInitDefaultValues() {
		minWidth = 80;// size of view
		minHeight = 48;
		backgroundColor = Color.parseColor("#4CAF50");// default color
		backgroundResId = R.drawable.background_transparent;
	}
	
	@Override
	protected void setAttributes(AttributeSet attrs) {
		super.setAttributes(attrs);
		if (!isInEditMode()) {
			getBackground().setAlpha(0);
		}
		iSchecked = attrs.getAttributeBooleanValue(MATERIALDESIGNXML, "checked", false);
		eventCheck = iSchecked;
		//添加监听器，如果点击了这个控件（不包括ball的区域），这个控件就开始判断是否是开启状态。
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setChecked(iSchecked ? false : true);
			}
		});
		
		float size = 20;
		String thumbSize = attrs.getAttributeValue(MATERIALDESIGNXML, "thumbSize");
		if (thumbSize != null) {
			size = Utils.dipOrDpToFloat(thumbSize);
		}
		ball = new Ball(getContext());
		setThumbParams(size);
		addView(ball);
		// 给圆球添加监听器，点击圆球后就开始判断是否进入开启状态
		ball.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				setChecked(iSchecked ? false : true);
			}
		});
	}

	private void setThumbParams(float size) {
		RelativeLayout.LayoutParams params = new LayoutParams(
				Utils.dpToPx(size, getResources()), Utils.dpToPx(size, getResources()));
		params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		ball.setLayoutParams(params);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isEnabled()) {
			isLastTouch = true;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				press = true;
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				requestDisallowInterceptTouchEvent(true);
				float x = event.getX();
				x = (x < ball.xIni) ? ball.xIni : x;
				x = (x > ball.xFin) ? ball.xFin : x;
				if (x > ball.xCen) {
					iSchecked = true;
				} else {
					iSchecked = false;
				}
				ViewHelper.setX(ball, x);
				ball.changeBackground();
				if (event.getX() <= getWidth() && event.getX() >= 0) {
					isLastTouch = false;
					press = false;
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP 
					|| event.getAction() == MotionEvent.ACTION_CANCEL) {
				requestDisallowInterceptTouchEvent(false);
				press = false;
				isLastTouch = false;
				if (eventCheck != iSchecked) {
					eventCheck = iSchecked;
					if (onCheckListener != null)
						onCheckListener.onCheck(iSchecked);
				}
				if (event.getX() <= getWidth() && event.getX() >= 0) {
					ball.animateCheck();
				} 
			}
		}
		return true;
	}
	
/*	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//return super.onInterceptTouchEvent(ev);
		return false;
	}
*/
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!placedBall)
			placeBall();

		// Crop line to transparent effect
		Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(),
				canvas.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas temp = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor((iSchecked) ? backgroundColor : Color.parseColor("#B0B0B0"));
		paint.setStrokeWidth(Utils.dpToPx(2, getResources()));
		temp.drawLine(getHeight() / 2, getHeight() / 2, getWidth() - getHeight() / 2, getHeight() / 2, paint);
		Paint transparentPaint = new Paint();
		transparentPaint.setAntiAlias(true);
		transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
		transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		temp.drawCircle(ViewHelper.getX(ball) + ball.getWidth() / 2,
				ViewHelper.getY(ball) + ball.getHeight() / 2, ball.getWidth() / 2, transparentPaint);
		canvas.drawBitmap(bitmap, 0, 0, new Paint());

		if (press) {
			paint.setColor((iSchecked) ? makePressColor(70) : Color.parseColor("#446D6D6D"));
			canvas.drawCircle(ViewHelper.getX(ball) + ball.getWidth() / 2, getHeight() / 2, getHeight() / 2, paint);
		}
		invalidate();
	}

	// Move ball to first position in view
	private boolean placedBall = false;

	private void placeBall() {
		ViewHelper.setX(ball, getHeight() / 2 - ball.getWidth() / 2);
		ball.xIni = ViewHelper.getX(ball);
		ball.xFin = getWidth() - getHeight() / 2 - ball.getWidth() / 2;
		ball.xCen = getWidth() / 2 - ball.getWidth() / 2;
		placedBall = true;
		ball.animateCheck();
	}

	// SETTERS AND GETTERS
	
	@Override
	public void setBackgroundColor(int color) {
		backgroundColor = color;
		if (isEnabled()) {
			beforeBackground = backgroundColor;
		}
	}
	
	public void setChecked(boolean check) {
		iSchecked = check;
		ball.animateCheck();
	}
	
	public boolean isChecked() {
		return iSchecked;
	}

	public void setThumbSize(float size) {
		setThumbParams(size);
	}

	
	private class Ball extends View {

		private float xIni, xFin, xCen;

		public Ball(Context context) {
			super(context);
			if (!isInEditMode()) {
				setBackgroundResource(R.drawable.background_switch_ball_uncheck);
			}
		}

		public void changeBackground() {
			if (iSchecked) {
				if (!isInEditMode()) {
					setBackgroundResource(R.drawable.background_checkbox);
					LayerDrawable layer = (LayerDrawable) getBackground();
					GradientDrawable shape = (GradientDrawable) layer
							.findDrawableByLayerId(R.id.shape_bacground);
					shape.setColor(backgroundColor);
				}

			} else {
				if (!isInEditMode()) {
					setBackgroundResource(R.drawable.background_switch_ball_uncheck);
				}
			}
		}

		public void animateCheck() {
			changeBackground();
			ObjectAnimator objectAnimator;
			if (iSchecked) {
				objectAnimator = ObjectAnimator.ofFloat(this, "x", ball.xFin);

			} else {
				objectAnimator = ObjectAnimator.ofFloat(this, "x", ball.xIni);
			}
			objectAnimator.setDuration(300);
			objectAnimator.start();
		}

	}
	
	public void setOncheckListener(OnCheckListener onCheckListener) {
		this.onCheckListener = onCheckListener;
	}

	public interface OnCheckListener {
		public void onCheck(boolean isChecked);
	}

}
