package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

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
import android.widget.TextView;

public class CheckBox extends CustomView {

	int backgroundColor = Color.parseColor("#4CAF50");

	Check checkView;

	boolean press = false;
	boolean check = false;

	OnCheckListener onCheckListener;

	public CheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
	}

	// Set atributtes of XML to View
	protected void setAttributes(AttributeSet attrs) {

		setBackgroundResource(R.drawable.background_checkbox);

		// Set size of view
		setMinimumHeight(Utils.dpToPx(48, getResources()));
		setMinimumWidth(Utils.dpToPx(48, getResources()));

		// Set background Color
		// Color by resource
		int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML,
				"background", -1);
		if (bacgroundColor != -1) {
			setBackgroundColor(getResources().getColor(bacgroundColor));
		} else {
			// Color by hexadecimal
			// Color by hexadecimal
			int background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
			if (background != -1)
				setBackgroundColor(background);
		}

		final boolean check = attrs.getAttributeBooleanValue(MATERIALDESIGNXML,
				"check", false);
			post(new Runnable() {

				@Override
				public void run() {
					setChecked(check);
					setPressed(false);
					changeBackgroundColor(getResources().getColor(
							android.R.color.transparent));
				}
			});

		checkView = new Check(getContext());
        checkView.setId(View.generateViewId());
		RelativeLayout.LayoutParams params = new LayoutParams(Utils.dpToPx(20,
				getResources()), Utils.dpToPx(20, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		checkView.setLayoutParams(params);
		addView(checkView);

        // Adding text view to checkbox
        int textResource = attrs.getAttributeResourceValue(ANDROIDXML, "text", -1);
        String text = null;

        if(textResource != -1) {
            text = getResources().getString(textResource);
        } else {
            text = attrs.getAttributeValue(ANDROIDXML, "text");
        }

        if(text != null) {
            params.removeRule(RelativeLayout.CENTER_IN_PARENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                    TextView textView = new TextView(getContext());
            RelativeLayout.LayoutParams textViewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            textViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, checkView.getId());
            textViewLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            textViewLayoutParams.setMargins(10, 0, 0, 0);
            textView.setLayoutParams(textViewLayoutParams);
            textView.setText(text);

            applyTextAppearance(textView, attrs);

            addView(textView);
        }
	}

    /**
     * Apply textAppearance based from attribute set xml to a specific TextView
     *
     * @param textView
     * @param attributeSet
     */
    private void applyTextAppearance(TextView textView, AttributeSet attributeSet) {
        int textAppearanceResource = attributeSet.getAttributeResourceValue(ANDROIDXML, "textAppearance", -1);

        if (textAppearanceResource != -1) {
            textView.setTextAppearance(getContext(), textAppearanceResource);
        }
    }

	@Override
	public void invalidate() {
		checkView.invalidate();
		super.invalidate();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		invalidate();
		if (isEnabled()) {
			isLastTouch = true;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				changeBackgroundColor((check) ? makePressColor() : Color
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
			paint.setColor((check) ? makePressColor() : Color
					.parseColor("#446D6D6D"));
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2,
					paint);
			invalidate();
		}
	}

	private void changeBackgroundColor(int color) {
		LayerDrawable layer = (LayerDrawable) getBackground();
		GradientDrawable shape = (GradientDrawable) layer
				.findDrawableByLayerId(R.id.shape_bacground);
		shape.setColor(color);
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

	@Override
	public void setBackgroundColor(int color) {
		backgroundColor = color;
		if (isEnabled())
			beforeBackground = backgroundColor;
		changeBackgroundColor(color);
	}

	public void setChecked(boolean check) {
		invalidate();
		this.check = check;
		setPressed(false);
		changeBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		if (check) {
			step = 0;
		}
		if (check)
			checkView.changeBackground();

	}

	public boolean isCheck() {
		return check;
	}

	// Indicate step in check animation
	int step = 0;

	// View that contains checkbox
	class Check extends View {

		Bitmap sprite;

		public Check(Context context) {
			super(context);
			setBackgroundResource(R.drawable.background_checkbox_uncheck);
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
				setBackgroundResource(R.drawable.background_checkbox_uncheck);
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			if (check) {
				if (step < 11){
					step++;
					invalidate();
				}
			} else {
				if (step >= 0){
					step--;
					invalidate();
				}
				if (step == -1){
					invalidate();
					changeBackground();
				}
			}
			Rect src = new Rect(40 * step, 0, (40 * step) + 40, 40);
			Rect dst = new Rect(0, 0, this.getWidth() - 2, this.getHeight());
			canvas.drawBitmap(sprite, src, dst, null);

		}

	}

	public void setOncheckListener(OnCheckListener onCheckListener) {
		this.onCheckListener = onCheckListener;
	}

	public interface OnCheckListener {
		public void onCheck(boolean check);
	}

}
