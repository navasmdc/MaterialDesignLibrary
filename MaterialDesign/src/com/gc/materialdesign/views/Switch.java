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

	int backgroundColor = Color.parseColor("#4CAF50");

	Ball ball;

	boolean check = false;
	boolean eventCheck = false;
	boolean press = false;

	OnCheckListener onCheckListener;

	public Switch(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (check)
					setChecked(false);
				else
					setChecked(true);
			}
		});
	}

	// Set atributtes of XML to View
	protected void setAttributes(AttributeSet attrs) {

		setBackgroundResource(R.drawable.background_transparent);

		// Set size of view
		setMinimumHeight(Utils.dpToPx(48, getResources()));
		setMinimumWidth(Utils.dpToPx(80, getResources()));

		// Set background Color
		// Color by resource
		int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML,
				"background", -1);
		if (bacgroundColor != -1) {
			setBackgroundColor(getResources().getColor(bacgroundColor));
		} else {
			// Color by hexadecimal
			int background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
			if (background != -1)
				setBackgroundColor(background);
		}

		check = attrs.getAttributeBooleanValue(MATERIALDESIGNXML, "check",
				false);
		eventCheck = check;
		ball = new Ball(getContext());
		RelativeLayout.LayoutParams params = new LayoutParams(Utils.dpToPx(20,
				getResources()), Utils.dpToPx(20, getResources()));
		params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		ball.setLayoutParams(params);
		addView(ball);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isEnabled()) {
			isLastTouch = true;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				press = true;
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				float x = event.getX();
				x = (x < ball.xIni) ? ball.xIni : x;
				x = (x > ball.xFin) ? ball.xFin : x;
				if (x > ball.xCen) {
					check = true;
				} else {
					check = false;
				}
				ViewHelper.setX(ball, x);
				ball.changeBackground();
				if ((event.getX() <= getWidth() && event.getX() >= 0)) {
					isLastTouch = false;
					press = false;
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP ||
					event.getAction() == MotionEvent.ACTION_CANCEL) {
				press = false;
				isLastTouch = false;
				if (eventCheck != check) {
					eventCheck = check;
					if (onCheckListener != null)
						onCheckListener.onCheck(check);
				}
				if ((event.getX() <= getWidth() && event.getX() >= 0)) {
					ball.animateCheck();
				}
			}
		}
		return true;
	}

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
		paint.setColor((check) ? backgroundColor : Color.parseColor("#B0B0B0"));
		paint.setStrokeWidth(Utils.dpToPx(2, getResources()));
		temp.drawLine(getHeight() / 2, getHeight() / 2, getWidth()
				- getHeight() / 2, getHeight() / 2, paint);
		Paint transparentPaint = new Paint();
		transparentPaint.setAntiAlias(true);
		transparentPaint.setColor(getResources().getColor(
				android.R.color.transparent));
		transparentPaint.setXfermode(new PorterDuffXfermode(
				PorterDuff.Mode.CLEAR));
		temp.drawCircle(ViewHelper.getX(ball) + ball.getWidth() / 2,
				ViewHelper.getY(ball) + ball.getHeight() / 2,
				ball.getWidth() / 2, transparentPaint);

		canvas.drawBitmap(bitmap, 0, 0, new Paint());

		if (press) {
			paint.setColor((check) ? makePressColor() : Color
					.parseColor("#446D6D6D"));
			canvas.drawCircle(ViewHelper.getX(ball) + ball.getWidth() / 2,
					getHeight() / 2, getHeight() / 2, paint);
		}
		invalidate();

	}

	/**
	 * Make a dark color to press effect
	 * 
	 * @return
	 */
	protected int makePressColor() {
		int r = (this.backgroundColor >> 16) & 0xFF;
		int g = (this.backgroundColor >> 8) & 0xFF;
		int b = (this.backgroundColor >> 0) & 0xFF;
		r = (r - 30 < 0) ? 0 : r - 30;
		g = (g - 30 < 0) ? 0 : g - 30;
		b = (b - 30 < 0) ? 0 : b - 30;
		return Color.argb(70, r, g, b);
	}

	// Move ball to first position in view
	boolean placedBall = false;

	private void placeBall() {
		ViewHelper.setX(ball, getHeight() / 2 - ball.getWidth() / 2);
		ball.xIni = ViewHelper.getX(ball);
		ball.xFin = getWidth() - getHeight() / 2 - ball.getWidth() / 2;
		ball.xCen = getWidth() / 2 - ball.getWidth() / 2;
		placedBall = true;
		ball.animateCheck();
	}

	// SETTERS

	@Override
	public void setBackgroundColor(int color) {
		backgroundColor = color;
		if (isEnabled())
			beforeBackground = backgroundColor;
		
	}

	public void setChecked(boolean check) {
		invalidate();
		this.check = check;
		eventCheck = check;
		ball.animateCheck();
	}

	public boolean isCheck() {
		return check;
	}

	class Ball extends View {

		float xIni, xFin, xCen;

		public Ball(Context context) {
			super(context);
			setBackgroundResource(R.drawable.background_switch_ball_uncheck);
		}

		public void changeBackground() {
			if (check) {
				setBackgroundResource(R.drawable.background_checkbox);
				LayerDrawable layer = (LayerDrawable) getBackground();
				GradientDrawable shape = (GradientDrawable) layer
						.findDrawableByLayerId(R.id.shape_bacground);
				shape.setColor(backgroundColor);
			} else {
				setBackgroundResource(R.drawable.background_switch_ball_uncheck);
			}
		}

		public void animateCheck() {
			changeBackground();
			ObjectAnimator objectAnimator;
			if (check) {
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
		public void onCheck(boolean check);
	}

}
