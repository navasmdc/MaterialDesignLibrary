package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

public abstract class Button extends RippleView {

	public Button(Context context, AttributeSet attrs) {
		super(context, attrs);
		onInitAttributes(attrs);
	}
	
	@Override
	protected void onInitDefaultValues() {
		backgroundColor = Color.parseColor("#2196f3");// 默认的背景色，蓝色
		///beforeBackground = backgroundColor;// error
	}
	
	protected void onInitAttributes(AttributeSet attrs) {
		setAttributes(attrs);
	}
	
	// ### RIPPLE EFFECT ###
	
	/**
	 * @return 涟漪的bitmap
	 */
	public Bitmap makeCircle() {
		// 画涟漪时要考虑到按钮的边界区域，不要把按钮的阴影边界也填满了
		Bitmap output = Bitmap.createBitmap(
				getWidth() - Utils.dpToPx(6, getResources()), 
				getHeight() - Utils.dpToPx(7, getResources()), Config.ARGB_8888);
		return makeCircleFromBitmap(output);
	}
	
	// Set color of background
	@Override
	public void setBackgroundColor(int color) {
		backgroundColor = color;
		if (isEnabled()) {
			beforeBackground = backgroundColor;
		}
		try {
			LayerDrawable layer = (LayerDrawable) getBackground();
			// 每个按钮的框架都是由drawable中的xml文件制定的，xml文件中都有一个item的id叫：shape_bacground
			GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
			/**
			 * 给这个图片设置背景色，因为图片的主体是透明的所以可以直接显示背景色
			 * 效果就是一个透明但有阴影的框架下有了背景色，这样的方式可以方便的设置不同颜色的按钮，让按钮看起来还是浑然一体
			 */
			shape.setColor(backgroundColor);
			/**
			 * 当重新设定背景色后，要检查涟漪颜色。如果已经设定了涟漪颜色，那么就用之前的。如果没设定就重新生成
			 */
			if (!settedRippleColor) {
				rippleColor = makePressColor(255);
			}
		} catch (Exception ex) {
			// Without bacground
		}
	}

	abstract public TextView getTextView();
	
}
