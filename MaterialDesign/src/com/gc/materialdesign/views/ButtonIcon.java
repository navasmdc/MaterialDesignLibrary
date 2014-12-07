package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ButtonIcon extends ButtonFloat {

	public ButtonIcon(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onInitDefaultValues() {
		super.onInitDefaultValues();
		rippleSpeed = 2;
		rippleSize = 5;
		backgroundResId = -1;
		// Background shape
		setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean returnBool = super.onTouchEvent(event);
		if(x != -1){
			x = getWidth() / 2;
			y = getHeight() / 2;
		}
		return returnBool;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (x != -1) {
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(backgroundColor);
			canvas.drawCircle(x, y, radius, paint);
			if(radius > getHeight()/rippleSize)
				radius += rippleSpeed;
			if(radius >= getWidth() / 2 - rippleSpeed){
				x = -1;
				y = -1;
				radius = getHeight()/rippleSize;
				if(isEnabled() && clickAfterRipple == true && onClickListener != null)
					onClickListener.onClick(this);
			}
		}
		invalidate();
	}

}
