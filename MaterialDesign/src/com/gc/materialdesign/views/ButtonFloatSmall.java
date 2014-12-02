package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.gc.materialdesign.R;

/**
 * @tips :很小的圆形按钮，上面可以添加图片
 * @date :2014-11-1
 */
public class ButtonFloatSmall extends ButtonFloat {

	public ButtonFloatSmall(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onInitDefaultValues() {
		backgroundColor = Color.parseColor("#1E88E5");// 默认的背景色，蓝色
		sizeRadius = 20;
		iconSize = 24;
		rippleSpeed = 2;
		rippleSize = 10;
		minWidth = sizeRadius * 2;// 40dp
		minHeight = sizeRadius * 2;// 40dp
		// Background shape
		if (!isInEditMode()) {
			setBackgroundResource(R.drawable.background_button_float);// 这是一个圆形的带阴影的图片
		}
		setBackgroundColor(backgroundColor);
	}


}
