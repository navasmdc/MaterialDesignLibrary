package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;


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
		super.onInitDefaultValues();
		/**
		 *  其实这里里面很多值在父控件中已经设置过了，这里可以不用super语句。
		 *  但为了方便理解和避免错误。用了super语句，super后将需要自定义的值又重新设置了一遍。
		 */
		sizeRadius = 20;
		rippleSize = 8;
		minWidth = sizeRadius * 2;// 40dp
		minHeight = sizeRadius * 2;// 40dp
	}


}
