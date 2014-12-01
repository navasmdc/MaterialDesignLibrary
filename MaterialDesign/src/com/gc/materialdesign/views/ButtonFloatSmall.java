package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

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
		sizeRadius = 20;
		sizeIcon = 24;
		rippleSpeed = 2;
		rippleSize = 10;
		minWidth = sizeRadius * 2;
		minHeight = sizeRadius * 2;
		// Background shape
		if (!isInEditMode()) {
			setBackgroundResource(R.drawable.background_button_float);// 这是一个圆形的带阴影的图片
		}
	}
	
	@Override
	protected void onInitAttributes(AttributeSet attrs) {
		super.onInitAttributes(attrs);
		// 设置按钮中图标的大小
		String iconSize = attrs.getAttributeValue(MATERIALDESIGNXML, "iconSize");
		if (iconSize != null) {
			sizeIcon = (int) Utils.dipOrDpToFloat(iconSize);
		}
		setPropertiesAndParams();
	}

	private void setPropertiesAndParams() {
		onInitDefaultValues();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				Utils.dpToPx(sizeIcon, getResources()), Utils.dpToPx(sizeIcon, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		icon.setLayoutParams(params);
	}

	/**
	 * 设置button中图片的大小，默认是居中显示的。 如果图片大小超过了按钮的大小，那么按钮会根据图片进行放大，直到能包含内部图片为止
	 * 
	 * @param size
	 */
	public void setIconSize(int size) {
		sizeIcon = size;
		// TODO：更新代码，进行重新绘制
		setPropertiesAndParams();
	}

	/**
	 * @return 按钮中心图片的大小
	 */
	public int getIconSize() {
		return sizeIcon;
	}

}
