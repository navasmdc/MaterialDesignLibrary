package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

public class ButtonImage extends ButtonFloat {

	public ButtonImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected void onInitDefaultValues() {
		super.onInitDefaultValues();
		iconSize = 24;
		sizeRadius = 28;
		rippleSpeed = 8;
		rippleSize = 5;
		minWidth = 20;
		minHeight = 20;
		backgroundResId = R.drawable.background_button_rectangle;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		if (x != -1) {
			Rect src = new Rect(0, 0, getWidth()-Utils.dpToPx(6, getResources()), getHeight()-Utils.dpToPx(7, getResources()));
			Rect dst = new Rect(Utils.dpToPx(6, getResources()), Utils.dpToPx(6, getResources()), getWidth()-Utils.dpToPx(6, getResources()), getHeight()-Utils.dpToPx(7, getResources()));
			canvas.drawBitmap(makeCircle(), src, dst, null);
		}
		invalidate();
	}
}
