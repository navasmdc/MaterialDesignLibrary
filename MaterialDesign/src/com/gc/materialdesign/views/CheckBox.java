package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

public class CheckBox extends CustomView {

	Check checkView;

	boolean press = false;
	boolean check = false;

	OnCheckListener onCheckListener;

	public CheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		backgroundColor = Color.parseColor("#4CAF50");
		minWidth = 48;
		minHeight = 48;
		setAttributes(attrs);
	}

	// Set atributtes of XML to View
	protected void setAttributes(AttributeSet attrs) {
		if (!isInEditMode()) {
			// 设置checkbox的背景色
			setBackgroundResource(R.drawable.background_checkbox);
		} else {
			setBackgroundResource(android.R.drawable.checkbox_on_background);
		}
		setViewSize();
		setBackgroundAttributes(attrs);

		boolean check = attrs.getAttributeBooleanValue(MATERIALDESIGNXML, "check", false);
		if (check) {
			post(new Runnable() {

				@Override
				public void run() {
					setChecked(true);
					setPressed(false);
					changeBackgroundColor(getResources().getColor(android.R.color.transparent));
				}
			});
		}

		float size = 20;
		String checkBoxSize = attrs.getAttributeValue(MATERIALDESIGNXML, "checkBoxSize");
		if (checkBoxSize != null) {
			size = Utils.dipOrDpToFloat(checkBoxSize);
		}

		checkView = new Check(getContext());
		RelativeLayout.LayoutParams params = new LayoutParams(
				Utils.dpToPx(size, getResources()), Utils.dpToPx(size, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		checkView.setLayoutParams(params);
		addView(checkView);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isEnabled()) {
			isLastTouch = true;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				changeBackgroundColor((check) ? makePressColor(70) : Color
						.parseColor("#446D6D6D"));
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				changeBackgroundColor(getResources().getColor(
						android.R.color.transparent));
				press = false;
				if ((event.getX() <= getWidth() && event.getX() >= 0)
						&& (event.getY() <= getHeight() && event.getY() >= 0)) {
					isLastTouch = false;
					check = !check;
					if (onCheckListener != null)
						onCheckListener.onCheck(check);
					if (check) {
						step = 0;
					}
					if (check)
						checkView.changeBackground();
				}
			}
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (press) {
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor((check) ? makePressColor(70) : Color
					.parseColor("#446D6D6D"));
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2,
					paint);
		}
		invalidate();
	}

	private void changeBackgroundColor(int color) {
		LayerDrawable layer = (LayerDrawable) getBackground();
		GradientDrawable shape = (GradientDrawable) layer
				.findDrawableByLayerId(R.id.shape_bacground);
		shape.setColor(color);
	}

	@Override
	public void setBackgroundColor(int color) {
		backgroundColor = color;
		if (isEnabled()) {
			 beforeBackground = backgroundColor;
		}
		changeBackgroundColor(color);
	}

	public void setChecked(boolean check) {
		this.check = check;
		setPressed(false);
		changeBackgroundColor(getResources().getColor(android.R.color.transparent));
		if (check) {
			step = 0;
		}
		if (check)
			checkView.changeBackground();
	}

	public boolean isChecked() {
		return check;
	}
	
	// Indicate step in check animation
	int step = 0;

	// View that contains checkbox
	class Check extends View {

		Bitmap sprite;

		public Check(Context context) {
			super(context);
			if (!isInEditMode()) {
				setBackgroundResource(R.drawable.background_checkbox_uncheck);
			}
			sprite = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.sprite_check);
		}

		public void changeBackground() {
			if (check) {
				setBackgroundResource(R.drawable.background_checkbox_check);
				LayerDrawable layer = (LayerDrawable) getBackground();
				GradientDrawable shape = (GradientDrawable) layer
						.findDrawableByLayerId(R.id.shape_bacground);
				shape.setColor(backgroundColor);
			} else {
				if (!isInEditMode()) {
					setBackgroundResource(R.drawable.background_checkbox_uncheck);
				}
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			if (check) {
				if (step < 11)
					step++;
			} else {
				if (step >= 0)
					step--;
				if (step == -1)
					changeBackground();
			}
			Rect src = new Rect(40 * step, 0, (40 * step) + 40, 40);
			Rect dst = new Rect(0, 0, this.getWidth() - 2, this.getHeight());
			if (!isInEditMode()) {
				canvas.drawBitmap(sprite, src, dst, null);
			}
			invalidate();

		}

	}

	public void setCheckBoxSize(float size) {
		removeView(checkView);
		RelativeLayout.LayoutParams params = new LayoutParams(
				Utils.dpToPx(size, getResources()), Utils.dpToPx(size, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		checkView.setLayoutParams(params);
		addView(checkView);
	}

	public void setOncheckListener(OnCheckListener onCheckListener) {
		this.onCheckListener = onCheckListener;
	}

	public interface OnCheckListener {
		public void onCheck(boolean isChecked);
	}

}
